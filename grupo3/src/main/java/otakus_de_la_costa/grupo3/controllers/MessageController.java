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

import otakus_de_la_costa.grupo3.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	@Autowired
	private MessageService mService;
	
	
	//CREATE MESSAGE
	@PostMapping("/addMessage")
	public ResponseEntity<Messages> createMessage(@RequestBody Messages message ){
		return new ResponseEntity<>(mService.createMessage(message),HttpStatus.CREATED);
	}
	//List messages
		@GetMapping("/listUsers")
		public List<Messages> listMessages(){
			return mService.listAllMessages();
		}
		//READ Message
		@GetMapping("/{id}")
		public ResponseEntity<Messages> readMessage(@PathVariable (value = "id") Long id){
			Messages message=mService.findMessageById(id);
			if(message!=null) {return  ResponseEntity.ok(mService.findMessageById(id));}
			return ResponseEntity.notFound().build();
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
	/*
	//READ MESSAGE
	@GetMapping("/{id}")
	public ResponseEntity<?> readMessage(@PathVariable (value = "id") Long id){
		Optional<MessageJPA> message= mService.findById(id);
		if (!message.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(message);

	}
	
	//READ ALL MESSAGES
	@GetMapping("/allMessages")
	public List<MessageJPA> readAll(){
		return mService.findAll();
	}
	
	//EDIT MESSAGE
	
	@PutMapping("/editMessage/{id}")
	public ResponseEntity<?> editMessage(@PathVariable (value = "id") Long idMessage,@RequestBody MessageJPA myMessage){
		Optional<MessageJPA> message= mService.findById(idMessage);
		if(!message.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		message.get().setContent(myMessage.getContent());
		message.get().setReadDate(myMessage.getReadDate());
		message.get().setReceptionDate(myMessage.getReceptionDate());
		message.get().setCreationDate(myMessage.getCreationDate());
		return ResponseEntity.status(HttpStatus.CREATED).body(mService.save(message.get()));
	}
	
	//DELETE MESSAGE
	
	@DeleteMapping("/deleteMessage({id}")
	public ResponseEntity<?> deleteMessage(@PathVariable (value = "id") Long id){
		if(!mService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		mService.delete(id);
		return ResponseEntity.ok().build();
	}
	*/
	
}
