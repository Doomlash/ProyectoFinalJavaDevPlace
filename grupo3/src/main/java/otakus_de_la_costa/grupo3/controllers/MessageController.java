package otakus_de_la_costa.grupo3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Message> createMessage(@RequestBody MessageRequest message){
		if(mService.createMessage(message)) {
			return  ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	//List messages
	@GetMapping()
	public ResponseEntity<List<Message>> listMessages(){
		return ResponseEntity.ok(mService.listAllMessages());
	}

	@PutMapping("/receive/{id}")
	public ResponseEntity<String> receiveMessage(@PathVariable (value = "id") Long id){
		mService.receiveMessage(id);
		return ResponseEntity.ok("mensaje recibido");
	}

	@PutMapping("/read/{id}")
	public ResponseEntity<String> readMessage(@PathVariable (value = "id") Long id){
		mService.readMessage(id);
		return ResponseEntity.ok("mensaje leido");
	}

}