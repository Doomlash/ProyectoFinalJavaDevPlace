package group3.middleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.middleware.model.MyUser;
import group3.middleware.model.request.RelationRequest;
import group3.middleware.services.implementation.IMyUser;

@RequestMapping("/middle/users")
@RestController
public class MyUserController {
    @Autowired
    private IMyUser iMU;

    @GetMapping
    public ResponseEntity<MyUser[]> listAll(){
        return iMU.listAllUsers();
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody MyUser user){
        return iMU.createU(user);
    }



    @GetMapping("/{idU}")
    public ResponseEntity<MyUser> get(@PathVariable("idU") Long idU){
        return iMU.readU(idU);
    }

    @PutMapping()
    public ResponseEntity<Integer> update(@RequestBody MyUser user){
        return iMU.updateMyUser(user);
    }

    @DeleteMapping("/{idU}")
    public ResponseEntity<Integer> delete(@PathVariable("idU") Long idU){
        return iMU.deleteU(idU);
    }

    /////////////////////////////////////////////////////////////////////////////

    @PostMapping("/contact")
    public ResponseEntity<Integer> addContact(@RequestBody RelationRequest rr){
        return iMU.addC(rr);
    }

    @PutMapping("/contact")
    public ResponseEntity<Integer> delContact(@RequestBody RelationRequest rr){
       return iMU.removeC(rr);
    }

    @PostMapping("/block")
    public ResponseEntity<Integer> addBlock(@RequestBody RelationRequest rr){
        return iMU.addB(rr);
    }

    @PutMapping("/block")
    public ResponseEntity<Integer> delBlock(@RequestBody RelationRequest rr){
        return iMU.removeB(rr);
    }
}
