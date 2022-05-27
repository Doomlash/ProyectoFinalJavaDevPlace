package group3.middleware.controllers;

import group3.middleware.model.Message;
import group3.middleware.model.MessageRequest;
import group3.middleware.services.implementation.IMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/middle/messages")
@RestController
public class MessagesController {
    @Autowired
    private IMessages iM;

    @PostMapping()
    public ResponseEntity<Message> create(@RequestBody MessageRequest message){
        return iM.createMessage(message);
    }

    @GetMapping()
    public ResponseEntity<List<Message>> listAll(){
        return iM.listAllMessages();
    }

    @PutMapping("/receive/{idM}")
    public ResponseEntity<String> receiveMessage(@PathVariable (value = "idM") Long idM){
        return iM.receiveMessage(idM);
    }

    @PutMapping("/read/{idM}")
    public ResponseEntity<String> readMessage(@PathVariable (value = "idM") Long idM){
        return iM.readMessage(idM);
    }



    @GetMapping("/translate")
    public Message translate(@RequestBody Message message) {
        return iM.translate(message);
    }

}
