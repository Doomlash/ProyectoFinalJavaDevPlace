package otakus_de_la_costa.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService uService;
	
	
	//CREATE ONE USER
	@PostMapping("/addUser")
	public ResponseEntity<?> createUser(@RequestBody MyUser user){
		return ResponseEntity.status(HttpStatus.CREATED).body(uService.save(user));
	}
	
	//READ USER
	
	@GetMapping("/{id}")
	public ResponseEntity<?> readUser(@PathVariable Long id){
		Optional<MyUser> user = uService.findById(id);//Se guarda la busqueda de ese id en un objeto de user
		if (!user.isPresent()) {//Se valida si existe
			return ResponseEntity.notFound().build();//Si no existe ese usuario devuelve 404
		}
		return ResponseEntity.ok(user); //Devuelve codigo 200 si esta todo ok
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@RequestBody MyUser userDetails,@PathVariable(value="id") Long userId)
	{
		Optional<MyUser> user = uService.findById(userId);
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		user.get().setName(userDetails.getName());
		user.get().setAge(userDetails.getAge());
		user.get().setAvatar(userDetails.getAvatar());
		user.get().setEmail(userDetails.getEmail());
		user.get().setLanguage(userDetails.getLanguage());
		user.get().setSurname(userDetails.getSurname());
		//user.get().setListBlock(userDetails.getListBlock());
		//user.get().setListContacts(userDetails.getListContacts());
		//user.get().setListGroups(userDetails.getListGroups());
		user.get().setUsername(userDetails.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(uService.save(user.get()));
	}
	
	//Delete user
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable (value = "id") Long userId){
		if(!uService.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		uService.delete(userId);
		return ResponseEntity.ok().build();
	}
	
	//List users
	
	@GetMapping("/listUsers")
	public List<MyUser> listUsers(){
		return uService.findAll();
	}

	
	
}
