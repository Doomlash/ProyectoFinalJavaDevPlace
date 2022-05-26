package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.GroupMembersJPA;
import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.model.RelationRequest;
import otakus_de_la_costa.grupo3.repositories.UserRepository;

//CRUD DE USUARIOS FUNCIONES PRINCIPALES
@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository uRepo;
	
	private MyUser mapearMyUser(MyUserJPA myUserJPA) {
        System.out.println("asdasdasdasd");
		MyUser myUser = new MyUser();
		myUser.setId(myUserJPA.getId());
		myUser.setUsername(myUserJPA.getUsername());
		myUser.setFirstName(myUserJPA.getFirstName());
		myUser.setLastName(myUserJPA.getLastName());
		myUser.setMail(myUserJPA.getMail());
		myUser.setLanguage(myUserJPA.getLanguage());
		myUser.setProfileImage(myUserJPA.getProfileImage());
		myUser.setBirthDate(myUserJPA.getBirthDate());
        System.out.println(myUserJPA.getContacts().size());
        for (MyUserJPA u : myUserJPA.getContacts()) {
            myUser.addContact(u.getId(),u.getUsername());
        }
        for (MyUserJPA u : myUserJPA.getBlocks()) {
            myUser.addBlock(u.getId(),u.getUsername());
        }
        for(GroupMembersJPA g : myUserJPA.getGroups()){
            myUser.addGroup(g.getId().getGroupId());
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
	public boolean createUser(MyUser myUser) {
		uRepo.save(mapearMyUserJPASinListas(myUser));
        return true;
		
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
	public MyUser updateMyUser(MyUser myUser, Long id) {
		Optional<MyUserJPA> optional = uRepo.findById(id);
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
        if(myUser.getActive()!=null){
            myUserJPA.setActive(myUser.getActive());
        }
        if(myUser.getBirthDate()!=null){
            myUserJPA.setBirthDate(myUser.getBirthDate());
        }
		MyUserJPA updateUser = uRepo.save(myUserJPA);
		return mapearMyUser(updateUser);
	}
	
	@Override
	public boolean deleteUser(Long id) {
		Optional<MyUserJPA> myUser=uRepo.findById(id);
		if(myUser.isPresent())
		{
			uRepo.delete(myUser.get());
			return true;
		}
		return false;
		
	}

    @Override
    @Transactional
    public void addContact(RelationRequest request) {
        uRepo.addContact(request.getRelationOwner(), request.getRelated());
    }

    @Override
    @Transactional
    public void deleteContact(RelationRequest request) {
        uRepo.deleteContact(request.getRelationOwner(), request.getRelated());
        
    }

    @Override
    @Transactional
    public void addBlock(RelationRequest request) {
        uRepo.addBlock(request.getRelationOwner(), request.getRelated());
        
    }

    @Override
    @Transactional
    public void deleteBlock(RelationRequest request) {
        uRepo.deleteBlock(request.getRelationOwner(), request.getRelated());
        
    }
	

	

	

	


}
