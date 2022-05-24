package group3.middleware.controllers;

import group3.middleware.model.Messages;
import group3.middleware.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/middleware/messages")
@RestController
public class MessagesController {
    @Autowired
    private MessagesService mService;

    @PostMapping("/{id}")
    public int create(@PathVariable("id") Long id, @RequestBody Messages message){
        return mService.createMessage(id,message);
    }

    @GetMapping("contact/{idU}/{idC}")
    public List<Messages> getByContact(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return mService.messagesByContact(idU,idC);
    }

    @GetMapping("group/{idU}/{idG}")
    public List<Messages> getByGroup(@PathVariable("idU") Long idU,@PathVariable("idG") Long idG){
        return mService.messageByGroup(idU, idG);
    }

    @GetMapping("/{idU}")
    public List<Messages> getAll(@PathVariable("idU") Long idU){
        return mService.getAllMessages(idU) ;
    }

    @DeleteMapping("/{idM}")
    public List<Messages> delete(@PathVariable("idM") Long idM){
        return mService.getAllMessages(idM) ;
    }


}
