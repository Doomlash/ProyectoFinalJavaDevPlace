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

import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.services.IUserService;
import otakus_de_la_costa.grupo3.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private IUserService uService;
	
	
	//CREATE ONE USER
	@PostMapping("/addUser")
	public ResponseEntity<MyUser> createUser(@RequestBody MyUser user){
		return new ResponseEntity<>(uService.createUser(user),HttpStatus.CREATED);
	}
	//List users
	@GetMapping("/listUsers")
	public List<MyUser> listUsers(){
		return uService.listAllUsers();
	}
	//READ USER
	@GetMapping("/{id}")
	public ResponseEntity<MyUser> readUser(@PathVariable (value = "id") Long id){
		MyUser myuser=uService.findUserById(id);
		if(myuser!=null) {return  ResponseEntity.ok(uService.findUserById(id));}
		return ResponseEntity.notFound().build();
	}
	
	//UPDATE USER
	@PutMapping("/{id}")
	public ResponseEntity<MyUser> updateUser(@PathVariable (value = "id")Long id,@RequestBody MyUser myUser){
		MyUser myNewUser=uService.updateMyUser(myUser, id);
		return new ResponseEntity<>(myNewUser,HttpStatus.OK);
	}
	
	//DELETE USER
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "id")Long id){
		boolean delete=uService.deleteUser(id);
		if(delete) {
		return new ResponseEntity<>("Usuario eliminado con exito",HttpStatus.OK);}
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	

	
	
}
