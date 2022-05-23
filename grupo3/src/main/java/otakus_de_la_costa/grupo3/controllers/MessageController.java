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

import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.services.IMenssageService;
import otakus_de_la_costa.grupo3.services.MessageService;

@RestController
@RequestMapping("/api/")
public class MessageController {
	@Autowired
	private IMenssageService mService;
	
	
	//CREATE MESSAGE
	@PostMapping("/users/{idUser}/addMessage/{idContact}")
	public ResponseEntity<Messages> createMessage(@RequestBody Messages message,@PathVariable(value = "idUser")Long idUser,@PathVariable(value = "idContact")Long idContact ){
		Messages messages=mService.createMessage(message, idUser, idContact);
		if(messages!=null) {
		return  ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	//List messages
		@GetMapping("/users/listMessages")
		public List<Messages> listMessages(){
			return mService.listAllMessages();
		}
		//READ Message
		@GetMapping("/{id}")
		public ResponseEntity<Messages> readMessage(@PathVariable (value = "id") Long id){
			Messages message=mService.findMessageById(id);
			if(message==null) {return ResponseEntity.notFound().build();}
			return  ResponseEntity.ok().build();
			
		}
		
		//UPDATE Message
		@PutMapping("/{id}")
		public ResponseEntity<Messages> updateMessage(@PathVariable (value = "id")Long id,@RequestBody Messages myMessage){
			Messages myNewMessage=mService.updateMessage(myMessage, id);
			return new ResponseEntity<>(myNewMessage,HttpStatus.OK);
		}
		
		//DELETE Message
		@DeleteMapping("/{id}")
		public ResponseEntity<String> deleteMessage(@PathVariable(value = "id")Long id){
			boolean delete=mService.deleteMessage(id);
			if(delete) {
			return new ResponseEntity<>("Mesaje eliminado con exito",HttpStatus.OK);}
			return ResponseEntity.notFound().build();
		}

	
}
