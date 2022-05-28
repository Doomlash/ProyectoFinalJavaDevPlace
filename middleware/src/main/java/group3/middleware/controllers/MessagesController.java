package group3.middleware.controllers;

import group3.middleware.model.Message;
import group3.middleware.model.request.MessageRequest;
import group3.middleware.services.implementation.IMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/middle/messages")
@RestController
public class MessagesController {
    @Autowired
    private IMessages iM;

    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody MessageRequest message){
        try{
            iM.createMessage(message);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<Message[]> listAll(){
        return iM.listAllMessages();
    }

    @PutMapping("/receive/{idM}")
    public ResponseEntity<Integer> receiveMessage(@PathVariable (value = "idM") Long idM){
        ResponseEntity<Integer> rRcm = iM.receiveMessage(idM);
        if(rRcm.getStatusCodeValue() == HttpStatus.OK.value()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(rRcm.getBody(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/read/{idM}")
    public ResponseEntity<Integer> readMessage(@PathVariable (value = "idM") Long idM){
        ResponseEntity<Integer> rRdm = iM.readMessage(idM);
        if(rRdm.getStatusCodeValue() == HttpStatus.OK.value()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(rRdm.getBody(),HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/translate")
    public Message translate(@RequestBody Message message) {
        return iM.translate(message);
    }

}
