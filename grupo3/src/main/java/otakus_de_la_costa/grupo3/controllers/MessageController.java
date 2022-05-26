package otakus_de_la_costa.grupo3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.services.IMenssageService;

@RestController
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	private IMenssageService mService;
	
	
	// //CREATE MESSAGE
	// @PostMapping()
	// public ResponseEntity<Message> createMessage(@RequestBody Message message){
	// 	Message messages=mService.createMessage(message);
	// 	if(messages!=null) {
	// 	return  ResponseEntity.ok().build();
	// 	}
	// 	return ResponseEntity.notFound().build();
	// }
    
	// //List messages
    // @GetMapping("/all")
    // public List<Message> listMessages(){
    //     return mService.listAllMessages();
    // }
    // //READ Message
    // @GetMapping("/{id}")
    // public ResponseEntity<Message> readMessage(@PathVariable (value = "id") Long id){
    //     Message message=mService.findMessageById(id);
    //     if(message==null) {return ResponseEntity.notFound().build();}
    //     return  ResponseEntity.ok().build();
        
    // }
    
    // //UPDATE Message
    // @PutMapping("/{id}")
    // public ResponseEntity<Message> updateMessage(@PathVariable (value = "id")Long id,@RequestBody Message myMessage){
    //     Message myNewMessage=mService.updateMessage(myMessage, id);
    //     return new ResponseEntity<>(myNewMessage,HttpStatus.OK);
    // }
    
    // //DELETE Message
    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteMessage(@PathVariable(value = "id")Long id){
    //     boolean delete=mService.deleteMessage(id);
    //     if(delete) {
    //     return new ResponseEntity<>("Mesaje eliminado con exito",HttpStatus.OK);}
    //     return ResponseEntity.notFound().build();
    // }

	
}
