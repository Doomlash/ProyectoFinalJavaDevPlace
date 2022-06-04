package group3.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.mvc.services.implementation.IMessages;

@RequestMapping("/mvc/messages")
@RestController
public class MessagesController {
    @Autowired
    private IMessages iM;

//    @PostMapping()
//    public Integer create(@RequestBody MessageRequest message){
//        return iM.createMessage(message);
//    }
//
//    @GetMapping()
//    public List<Message> listAll(){
//        return iM.listAllMessages();
//    }
//
//    @PutMapping("/receive/{idM}")
//    public Integer receiveMessage(@PathVariable (value = "idM") Long idM){
//        return iM.receiveMessage(idM);
//    }
//
//    @PutMapping("/read/{idM}")
//    public Integer readMessage(@PathVariable (value = "idM") Long idM){
//        return iM.readMessage(idM);
//    }
//
//
//
//    @PostMapping("/translate/{langU}")
//    public Message translate(@RequestBody Message message,@PathVariable("langU") String langU) {
//        return iM.translate(message,langU);
//    }

}
