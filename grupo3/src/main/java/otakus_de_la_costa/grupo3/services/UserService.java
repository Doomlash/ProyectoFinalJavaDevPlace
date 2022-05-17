package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.model.MyUser;

import otakus_de_la_costa.grupo3.reposiories.UserRepository;

//CRUD DE USUARIOS FUNCIONES PRINCIPALES
@Service
public class UserService {
	@Autowired
	private UserRepository uRepo;
	
	//GET ALL USER
	
	public List<MyUser> findAll(){
		return uRepo.findAll();
	}
	
	//GET ONE USER
	
	public Optional<MyUser> findById(Long id){
		if(uRepo.existsById(id)) {
			return uRepo.findById(id);
		}
		return null;
	}
	
	
	//POST USER
	
	public MyUser save(MyUser user) {
		return uRepo.save(user);
	}
	
	//PUT USER
	
	public MyUser update(MyUser user) {
		if(uRepo.existsById(user.getId())) {
			return uRepo.save(user);
		}
		return null;
	}
	
	//DELETE USER
	
	public boolean delete(Long id) {
		if(uRepo.existsById(id)) {
			uRepo.deleteById(id);
			return true;
		}
		return false;
	}
}
