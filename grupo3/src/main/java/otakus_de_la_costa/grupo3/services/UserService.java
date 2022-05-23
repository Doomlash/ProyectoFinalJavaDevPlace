package otakus_de_la_costa.grupo3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.repositories.ContactRepository;
import otakus_de_la_costa.grupo3.repositories.MessageRepository;
import otakus_de_la_costa.grupo3.repositories.UserRepository;
import otakus_de_la_costa.grupo3.repositories.UserRepository;

//CRUD DE USUARIOS FUNCIONES PRINCIPALES
@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private ContactRepository cRepo;
	
	@Autowired
	private MessageRepository mRepo;
	
	//GUARDADO DEL OBJETO JSON A JPA
	@Override
	public MyUser createUser(MyUser myUser) {
		MyUserJPA myUserJPA= mapearMyUserJPA(myUser);
		MyUserJPA  newUserJPA = uRepo.save(myUserJPA);
		MyUser response = mapearMyUser(newUserJPA);
		return response;
		
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
		myUser.setActive(myUserJPA.getActive());
		myUser.setBirthDate(myUserJPA.getBirthDate());
		return myUser;
	}
	

	
	private MyUserJPA mapearMyUserJPA(MyUser myUser) {
		MyUserJPA myUserJPA = new MyUserJPA();
		myUserJPA.setUsername(myUser.getUsername());
		myUserJPA.setFirstName(myUser.getFirstName());
		myUserJPA.setLastName(myUser.getLastName());
		myUserJPA.setMail(myUser.getMail());
		myUserJPA.setLanguage(myUser.getLanguage());
		myUserJPA.setProfileImage(myUser.getProfileImage());
		myUserJPA.setActive(myUser.getActive());
		myUserJPA.setBirthDate(myUser.getBirthDate());
		return myUserJPA;
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
		Optional<MyUserJPA> myUserJPA = uRepo.findById(id);
		myUserJPA.get().setUsername(myUser.getUsername());
		myUserJPA.get().setFirstName(myUser.getFirstName());
		myUserJPA.get().setLastName(myUser.getLastName());
		myUserJPA.get().setMail(myUser.getMail());
		myUserJPA.get().setLanguage(myUser.getLanguage());
		myUserJPA.get().setProfileImage(myUser.getProfileImage());
		myUserJPA.get().setActive(myUser.getActive());
		myUserJPA.get().setBirthDate(myUser.getBirthDate());
		MyUserJPA updateUser = uRepo.save(myUserJPA.get());
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
	

	

	

	


}
