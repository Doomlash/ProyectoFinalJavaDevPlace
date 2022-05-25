package group3.middleware.controllers;

import group3.middleware.model.Messages;
import group3.middleware.services.implementation.IMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/middleware/messages")
@RestController
public class MessagesController {
    @Autowired
    private IMessages iM;

    @PostMapping("/{idU}")
    public int create(@PathVariable("idU") Long idU, @RequestBody Messages message){
        return iM.create(idU,message);
    }

    @GetMapping("contact/{idU}/{idC}")
    public List<Messages> getByContact(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return iM.messagesByContact(idU,idC);
    }

    @GetMapping("group/{idU}/{idG}")
    public List<Messages> getByGroup(@PathVariable("idU") Long idU,@PathVariable("idG") Long idG){
        return iM.messageByGroup(idU, idG);
    }

    @GetMapping("/{idU}")
    public List<Messages> getAll(@PathVariable("idU") Long idU){
        return iM.getAll(idU) ;
    }

    @DeleteMapping("/{idM}")
    public List<Messages> delete(@PathVariable("idM") Long idM){
        return iM.getAll(idM) ;
    }

    @GetMapping("/translate")
    public Messages translate(@RequestBody Messages message) {
        return iM.translate(message);
    }

}
