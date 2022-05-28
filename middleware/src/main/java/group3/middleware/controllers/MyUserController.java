package group3.middleware.controllers;

import group3.middleware.model.MyUser;
import group3.middleware.model.request.RelationRequest;
import group3.middleware.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static group3.middleware.model.Constants.NULL_ID;

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
        ResponseEntity<Integer> rc = iMU.createU(user);
        if(rc.getStatusCodeValue() == HttpStatus.OK.value()){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(rc.getBody(),HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/{idU}")
    public ResponseEntity<MyUser> get(@PathVariable("idU") Long idU){
        ResponseEntity<MyUser> rg = iMU.readU(idU);
        if(rg.getStatusCodeValue() == HttpStatus.OK.value()){
            return rg;
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping()
    public ResponseEntity<Integer> update(@RequestBody MyUser user){
        if(user.getId()==null){
            return ResponseEntity.badRequest().body(NULL_ID);
        }
        ResponseEntity<Integer> ru = iMU.updateMyUser(user);
        if(ru.getStatusCodeValue() == HttpStatus.OK.value()){
            return ru;
        }
        else{
            return new ResponseEntity<>(ru.getBody(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idU}")
    public ResponseEntity<Integer> delete(@PathVariable("idU") Long idU){
        try{
            iMU.deleteU(idU);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /////////////////////////////////////////////////////////////////////////////

    @PostMapping("/contact")
    public ResponseEntity<Integer> addContact(@RequestBody RelationRequest rr){
        ResponseEntity<Integer> rAc = iMU.addC(rr);
        if(rAc.getStatusCodeValue() == HttpStatus.CREATED.value()){
            return rAc;
        }else{
            return ResponseEntity.badRequest().body(rAc.getBody());
        }
    }

    @PutMapping("/contact")
    public ResponseEntity<Integer> delContact(@RequestBody RelationRequest rr){
        ResponseEntity<Integer> rDc = iMU.removeC(rr);
        if(rDc.getStatusCodeValue() == HttpStatus.OK.value()){
            return rDc;
        }else{
            return ResponseEntity.badRequest().body(rDc.getBody());
        }
    }

    @PostMapping("/block")
    public ResponseEntity<Integer> addBlock(@RequestBody RelationRequest rr){
        ResponseEntity<Integer> rAb = iMU.addB(rr);
        if(rAb.getStatusCodeValue() == HttpStatus.CREATED.value()){
            return rAb;
        }else{
            return ResponseEntity.badRequest().body(rAb.getBody());
        }
    }

    @PutMapping("/block")
    public ResponseEntity<Integer> delBlock(@RequestBody RelationRequest rr){
        ResponseEntity<Integer> rDd = iMU.removeB(rr);
        if(rDd.getStatusCodeValue() == HttpStatus.OK.value()){
            return rDd;
        }else{
            return ResponseEntity.badRequest().body(rDd.getBody());
        }
    }
}
