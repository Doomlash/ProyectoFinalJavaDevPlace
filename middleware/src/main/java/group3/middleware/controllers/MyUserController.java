package group3.middleware.controllers;

import group3.middleware.model.MyUser;
import group3.middleware.model.RelationRequest;
import group3.middleware.services.MyUserService;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Relation;
import java.util.List;

@RequestMapping("/middle/users")
@RestController
public class MyUserController {
    @Autowired
    private IMyUser iMU;

    @GetMapping
    public ResponseEntity<List<MyUser>> listAll(){
        return iMU.listAllUsers();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody MyUser user){
        return iMU.createU(user);
    }



    @GetMapping("/{idU}")
    public ResponseEntity<MyUser> get(@PathVariable("idU") Long idU){
        return iMU.readU(idU);
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody MyUser user){
        return iMU.updateMyUser(user);
    }

    @DeleteMapping("/{idU}")
    public ResponseEntity<String> delete(@PathVariable("idU") Long idU){
        return iMU.deleteU(idU);
    }

    /////////////////////////////////////////////////////////////////////////////

    @PostMapping("/contact")
    public ResponseEntity<String> addContact(@RequestBody RelationRequest rr){
        return iMU.addC(rr);
    }

    @DeleteMapping("/contact")
    public ResponseEntity<String> delContact(@RequestBody RelationRequest rr){
        return iMU.removeC(rr);
    }

    @PostMapping("/block")
    public ResponseEntity<String> addBlock(@RequestBody RelationRequest rr){
        return iMU.addB(rr);
    }

    @DeleteMapping("/block")
    public ResponseEntity<String> delBlock(@RequestBody RelationRequest rr){
        return iMU.removeB(rr);
    }
}
