package otakus_de_la_costa.grupo3.controllers;

import java.util.List;

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
import otakus_de_la_costa.grupo3.model.RelationRequest;
import otakus_de_la_costa.grupo3.services.IUserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private IUserService uService;
	
	
	//CREATE ONE USER
	@PostMapping()
	public ResponseEntity<String> createUser(@RequestBody MyUser user){
		return new ResponseEntity<>(String.valueOf(uService.createUser(user)),HttpStatus.CREATED);
	}

	//List users
	@GetMapping()
	public List<MyUser> listUsers(){
		return uService.listAllUsers();
	}

    //add a contact
    @PostMapping("/contact")
    public ResponseEntity<String> addContact(@RequestBody RelationRequest request){
        uService.addContact(request);
        return new ResponseEntity<String>("creado con exito", HttpStatus.CREATED);
    }

    //remove contact
    @DeleteMapping("/contact")
    public ResponseEntity<String> removeContact(@RequestBody RelationRequest request){
        uService.deleteContact(request);
        return new ResponseEntity<String>("borrado con exito", HttpStatus.OK);
    }

    //add block
    @PostMapping("/block")
    public ResponseEntity<String> addBlock(@RequestBody RelationRequest request){
        uService.addBlock(request);
        return new ResponseEntity<String>("creado con exito", HttpStatus.CREATED);
    }

    //remove block
    @DeleteMapping("/block")
    public ResponseEntity<String> removeBlock(@RequestBody RelationRequest request){
        uService.deleteBlock(request);
        return new ResponseEntity<String>("borrado con exito", HttpStatus.OK);
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
