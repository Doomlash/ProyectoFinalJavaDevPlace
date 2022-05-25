package group3.middleware.controllers;

import group3.middleware.model.MyUser;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/middleware/user")
@RestController
public class MyUserController {
    @Autowired
    private IMyUser iMU;

    @PostMapping
    public int create(@RequestBody MyUser user){
        return iMU.createUser(user);
    }

    @GetMapping("/{id}")
    public MyUser get(@PathVariable("id") Long id){
        return iMU.readUser(id);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable("id") Long id,@RequestBody MyUser user){
        return iMU.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") Long id){
        return iMU.deleteUser(id);
    }

    /////////////////////////////////////////////////////////////////////////////

    @PostMapping("/contact/{idU}/{idC}")
    public int addContact(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return iMU.addContact(idU, idC);
    }

    @PostMapping("/block/{idU}/{idC}")
    public int addBlock(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return iMU.addBlock(idU, idC);
    }

    @DeleteMapping("/contact/{idU}/{idC}")
    public int delContact(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return iMU.deleteContact(idU, idC);
    }

    @DeleteMapping("/block/{idU}/{idC}")
    public int delBlock(@PathVariable("idU") Long idU,@PathVariable("idC") Long idC){
        return iMU.deleteBlock(idU, idC);
    }
}
