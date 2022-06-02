package group3.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.mvc.model.MyUser;
import group3.mvc.services.connection.Connection;
import group3.mvc.services.implementation.IMyUser;

@RequestMapping("/mvc/users")
@RestController
public class MyUserController {
    @Autowired
    private IMyUser iMU;

    @GetMapping
    public List<MyUser> listAll(){
        System.out.println(Connection.getToken());
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
