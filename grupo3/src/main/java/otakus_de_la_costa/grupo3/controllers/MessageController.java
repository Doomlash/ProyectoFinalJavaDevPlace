package otakus_de_la_costa.grupo3.controllers;

import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.Message;
import otakus_de_la_costa.grupo3.model.MessageRequest;
import otakus_de_la_costa.grupo3.services.IMenssageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	@Autowired
	private IMenssageService mService;
	
	
	//CREATE MESSAGE
	@PostMapping()
	public ResponseEntity<Integer> createMessage(@RequestBody MessageRequest message){
        try{
            mService.createMessage(message);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (JpaSystemException e){
            if (e.getRootCause().getClass()==SQLException.class) {
                SQLException sqlException = (SQLException) e.getRootCause();
                return new ResponseEntity<>(Integer.valueOf(sqlException.getSQLState()),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
    
	//List messages
    @GetMapping()
    public ResponseEntity<List<Message>> listMessages(){
        return ResponseEntity.ok(mService.listAllMessages());
    }
    
    @PutMapping("/receive/{id}")
    public ResponseEntity<Integer> receiveMessage(@PathVariable (value = "id") Long id){
        int response = mService.receiveMessage(id);
        if(response==OK){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<Integer> readMessage(@PathVariable (value = "id") Long id){
        int response =  mService.readMessage(id);
        if(response==OK){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
       
    }
}
