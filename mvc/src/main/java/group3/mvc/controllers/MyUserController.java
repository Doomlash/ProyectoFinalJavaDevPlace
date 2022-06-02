package group3.mvc.controllers;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.RelationRequest;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mvc/users")
@RestController
public class MyUserController {
    @Autowired
    private IMyUser iMU;

    @GetMapping
    public List<MyUser> listAll(){
        return iMU.listAllUsers();
    }


    @GetMapping("/{idU}")
    public MyUser getByid(@PathVariable("idU") Long idU){
        return iMU.readUById(idU);
    }

    @GetMapping("/byUsername/{username}")
    public MyUser getByUsername(@PathVariable("username") String username){
        return iMU.readUByUsername(username);
    }


    @PutMapping()
    public Integer update(@RequestBody MyUser user){
        return iMU.updateMyUser(user);
    }

    @DeleteMapping("/{idU}")
    public Integer delete(@PathVariable("idU") Long idU){
        return iMU.deleteU(idU);
    }

    /////////////////////////////////////////////////////////////////////////////

////    @PostMapping("/contact")
////    public Integer addContact(@RequestBody RelationRequest rr){
////        return iMU.addC(rr);
////    }
//
//    @PutMapping("/contact")
//    public Integer delContact(@RequestBody RelationRequest rr){
//        return iMU.removeC(rr);
//    }
//
//    @PostMapping("/block")
//    public Integer addBlock(@RequestBody RelationRequest rr){
//        return iMU.addB(rr);
//    }
//
//    @PutMapping("/block")
//    public Integer delBlock(@RequestBody RelationRequest rr){
//        return iMU.removeB(rr);
//    }
}
