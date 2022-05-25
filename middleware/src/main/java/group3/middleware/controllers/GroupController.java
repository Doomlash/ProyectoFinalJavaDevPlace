package group3.middleware.controllers;

import group3.middleware.model.Group;
import group3.middleware.services.implementation.IGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/middleware/")
@RestController
public class GroupController {
    @Autowired
    private IGroup iG;

    @PostMapping
    public int create(@RequestBody Group group){
        return iG.createGroup(group);
    }

    @GetMapping("/{idG}")
    public Group get(@PathVariable("idG") Long idG){
        return iG.readGroup(idG);
    }

    @PutMapping("/{idG}")
    public int update(@PathVariable("idG") Long idG,@RequestBody Group group){
        return iG.updateGroup(idG,group);
    }

    @DeleteMapping("/{idG}")
    public int delete(@PathVariable("idG") Long idG){
        return iG.deleteGroup(idG);
    }

    @PostMapping("/{idU}/{idG}")
    public int add(@PathVariable("idU") Long idU,@PathVariable("idG") Long idG){
        return iG.addUserToGroup(idU,idG);
    }

    @DeleteMapping("/{idU}/{idG}")
    public int drop(@PathVariable("idU") Long idU,@PathVariable("idG") Long idG){
        return iG.deleteUserToGroup(idU,idG);
    }


}
