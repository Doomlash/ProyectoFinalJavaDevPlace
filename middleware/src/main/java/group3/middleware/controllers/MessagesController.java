package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import group3.middleware.services.interfaces.IMessages;


@RequestMapping("/middle/messages")
@RestController
public class MessagesController {
    @Autowired
    private IMessages iM;


    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody MessageRequest message){
            return iM.createMessage(message);
    }

    @GetMapping()
    public ResponseEntity<Message[]> listAll(){
        return iM.listAllMessages();
    }

    @PutMapping("/receive/{idM}")
    public ResponseEntity<Integer> receiveMessage(@PathVariable (value = "idM") Long idM){
        return iM.receiveMessage(idM);
    }

    @PutMapping("/read/{idM}")
    public ResponseEntity<Integer> readMessage(@PathVariable (value = "idM") Long idM){
        return iM.readMessage(idM);
    }



    @PostMapping("/translate/{langU}")
    public ResponseEntity<Message> translate(@RequestBody Message message,@PathVariable("langU") String langU) {
        return ResponseEntity.ok().body(iM.translate(message,langU));
    }
}
