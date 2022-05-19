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

import otakus_de_la_costa.grupo3.model.Messages;
import otakus_de_la_costa.grupo3.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	@Autowired
	private MessageService mService;
	
	
	//CREATE MESSAGE
	@PostMapping("/addMessage")
	public ResponseEntity<?> createMessage(@RequestBody Messages message ){
		return ResponseEntity.status(HttpStatus.CREATED).body(mService.save(message));
	}
	
	//READ MESSAGE
	@GetMapping("/{id}")
	public ResponseEntity<?> readMessage(@PathVariable (value = "id") Long id){
		Optional<Messages> message= mService.findById(id);
		if (!message.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(message);

	}
	
	//READ ALL MESSAGES
	@GetMapping("/allMessages")
	public List<Messages> readAll(){
		return mService.findAll();
	}
	
	//EDIT MESSAGE
	
	@PutMapping("/editMessage/{id}")
	public ResponseEntity<?> editMessage(@PathVariable (value = "id") Long idMessage,@RequestBody Messages myMessage){
		Optional<Messages> message= mService.findById(idMessage);
		if(!message.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		message.get().setContent(myMessage.getContent());
		message.get().setDateRead(myMessage.getDateRead());
		message.get().setDateReceive(myMessage.getDateReceive());
		message.get().setDateSend(myMessage.getDateSend());
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
	
	
}
