package otakus_de_la_costa.grupo3.controllers;

import static otakus_de_la_costa.grupo3.model.Constants.NULL_ID;
import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
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
	

	//List users
	@GetMapping()
	public List<MyUser> listUsers(){
		return uService.listAllUsers();
	}

    //add a contact
    @PostMapping("/contact")
    public ResponseEntity<Integer> addContact(@RequestBody RelationRequest request){
        try{
            uService.addContact(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    //remove contact
    @PutMapping("/contact")
    public ResponseEntity<Integer> removeContact(@RequestBody RelationRequest request){
        int response = uService.deleteContact(request);
        if(response==OK){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    //add block
    @PostMapping("/block")
    public ResponseEntity<Integer> addBlock(@RequestBody RelationRequest request){
        try{
            uService.addBlock(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    //remove block
    @PutMapping("/block")
    public ResponseEntity<Integer> removeBlock(@RequestBody RelationRequest request){
        int response = uService.deleteBlock(request);
        if(response==OK){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    } 

	//READ USER
	@GetMapping("/{id}")
	public ResponseEntity<MyUser> readUser(@PathVariable (value = "id") Long id){
		MyUser myUser=uService.findUserById(id);
		if(myUser!=null) {return  ResponseEntity.ok(myUser);}
		return ResponseEntity.notFound().build();
	}

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<MyUser> getByUsername(@PathVariable (value = "username")String username){
        MyUser myUser=uService.findByUsername(username);
        if(myUser!=null) {return  ResponseEntity.ok(myUser);}
		return ResponseEntity.notFound().build();
    }
	
	//UPDATE USER
	@PutMapping()
	public ResponseEntity<Integer> updateUser(@RequestBody MyUser myUser){
		if(myUser.getId()==null){
            return ResponseEntity.badRequest().body(NULL_ID);
        }
        int response = uService.updateMyUser(myUser);
        if(response == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
	}
	
	//DELETE USER
	@DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable(value = "id")Long id){
            try{
                uService.deleteUser(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }  catch (JpaSystemException e){
                if (e.getRootCause().getClass()==SQLException.class) {
                    return new ResponseEntity<>(Integer.valueOf(((SQLException) e.getRootCause()).getSQLState()),HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }catch(DataIntegrityViolationException e){
                return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
            }
    }
}
