package otakus_de_la_costa.grupo3.services;

import static otakus_de_la_costa.grupo3.model.Constants.IDS_NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupMembersJPA;
import otakus_de_la_costa.grupo3.database.MessageJPA;
import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.RelationRequest;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;
import otakus_de_la_costa.grupo3.repositories.UserRepository;

//CRUD DE USUARIOS FUNCIONES PRINCIPALES
@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository uRepo;

    @Autowired
    private MessageRepository mRepo;
	
	private Message mapMessageJPAToMessage(MessageJPA m){
        return new Message(
            m.getId(),
            m.getContent(),
            m.getLanguage(),
            m.getCreationDate(),
            m.getReceptionDate(),
            m.getReadDate(),
            m.getSender().getId(),
            m.getReceiver().getId()
        );
    }
    
    private MyUser mapearMyUser(MyUserJPA myUserJPA) {
		MyUser myUser = new MyUser();
		myUser.setId(myUserJPA.getId());
		myUser.setUsername(myUserJPA.getUsername());
		myUser.setFirstName(myUserJPA.getFirstName());
		myUser.setLastName(myUserJPA.getLastName());
		myUser.setMail(myUserJPA.getMail());
		myUser.setLanguage(myUserJPA.getLanguage());
		myUser.setProfileImage(myUserJPA.getProfileImage());
		myUser.setBirthDate(myUserJPA.getBirthDate());
        for (MyUserJPA u : myUserJPA.getContacts()) {
            myUser.addContact(u.getId(),u.getUsername());
        }
        for (MyUserJPA u : myUserJPA.getBlocks()) {
            myUser.addBlock(u.getId(),u.getUsername());
        }
        for(GroupMembersJPA g : myUserJPA.getGroups()){
            myUser.addGroup(g.getId().getGroupId());
            for(MessageJPA m : mRepo.getMessageFromGroup(g.getId().getGroupId(),myUserJPA.getId())){
                myUser.addReceived(mapMessageJPAToMessage(m));
            }
        }
        for(MessageJPA m : myUserJPA.getSent()){
            myUser.addSent(mapMessageJPAToMessage(m));
        }
        for(MessageJPA m : myUserJPA.getReceived()){
            myUser.addReceived(mapMessageJPAToMessage(m));
        }
		return myUser;
	}

	private MyUserJPA mapearMyUserJPASinListas(MyUser myUser) {
		MyUserJPA u = new MyUserJPA();
		u.setUsername(myUser.getUsername());
        u.setMail(myUser.getMail());
        u.setFirstName(myUser.getFirstName());
        u.setLastName(myUser.getLastName());
        u.setLanguage(myUser.getLanguage());
        u.setBirthDate(myUser.getBirthDate());
        u.setProfileImage(myUser.getProfileImage());
		return u;
	}

    //GUARDADO DEL OBJETO JSON A JPA
	@Override
	public int createUser(MyUser myUser) {
        int response = 0;
        if(uRepo.findByUsername(myUser.getUsername()).isPresent()){
            response++;
        }
        if(uRepo.findByMail(myUser.getMail()).isPresent()){
            response+=2;
        }
        if(response == 0){
            uRepo.save(mapearMyUserJPASinListas(myUser));
        }
        return response;
		
	}
	
	@Override
	public List<MyUser> listAllUsers() {
		List<MyUserJPA> users = uRepo.findAll();
		return users.stream().map(myUserJPA -> mapearMyUser(myUserJPA)).collect(Collectors.toList());
	}
	
	@Override
	public MyUser findUserById(Long id) {
		Optional<MyUserJPA> myUserJPA= uRepo.findById(id);
		if(myUserJPA.isPresent()) {
		    return mapearMyUser(myUserJPA.get());
		}
		return null;
	}
	@Override
	public int updateMyUser(MyUser myUser) {
        int response = 0;
        if(uRepo.findByUsername(myUser.getUsername()).isPresent()){
            response++;
        }
        if(uRepo.findByMail(myUser.getMail()).isPresent()){
            response+=2;
        }
        if(response == 0){
            Optional<MyUserJPA> optional = uRepo.findById(myUser.getId());
            if(optional.isEmpty()){
                return NOT_FOUND;
            }
            MyUserJPA myUserJPA = optional.get();
            if(myUser.getUsername()!=null){
                myUserJPA.setUsername(myUser.getUsername());
            }
            if(myUser.getFirstName()!=null){
                myUserJPA.setFirstName(myUser.getFirstName());
            }
            if(myUser.getLastName()!=null){
                myUserJPA.setLastName(myUser.getLastName());
            }
            if(myUser.getMail()!=null){
                myUserJPA.setMail(myUser.getMail());
            }
            if(myUser.getLanguage()!=null){
                myUserJPA.setLanguage(myUser.getLanguage());
            }
            if(myUser.getProfileImage()!=null){
                myUserJPA.setProfileImage(myUser.getProfileImage());
            }
            if(myUser.getBirthDate()!=null){
                myUserJPA.setBirthDate(myUser.getBirthDate());
            }
            uRepo.save(myUserJPA);
        }
        return response;
	}
	
	@Override
	public void deleteUser(Long id) {
        uRepo.deleteById(id);
	}

    @Override
    @Transactional
    public void addContact(RelationRequest request) {
        uRepo.addContact(request.getRelationOwner(), request.getRelated());
    }

    @Override
    @Transactional
    public int deleteContact(RelationRequest request) {
        if(uRepo.findById(request.getRelationOwner()).isEmpty() || uRepo.findById(request.getRelated()).isEmpty()){
            return IDS_NOT_FOUND;
        }
        uRepo.deleteContact(request.getRelationOwner(), request.getRelated());
        return OK;
    }

    @Override
    @Transactional
    public void addBlock(RelationRequest request) {
        uRepo.addBlock(request.getRelationOwner(), request.getRelated());
        
    }

    @Override
    @Transactional
    public int deleteBlock(RelationRequest request) {
        
        if(uRepo.findById(request.getRelationOwner()).isEmpty() || uRepo.findById(request.getRelated()).isEmpty()){
            return IDS_NOT_FOUND;
        }
        uRepo.deleteBlock(request.getRelationOwner(), request.getRelated());
        return OK;
    }
	

	

	

	


}
