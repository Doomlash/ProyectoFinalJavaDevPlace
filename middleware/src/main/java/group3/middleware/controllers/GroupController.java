package group3.middleware.controllers;

import group3.middleware.model.Group;
import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import group3.middleware.services.implementation.IGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static group3.middleware.model.Constants.NOT_FOUND;
import static group3.middleware.model.Constants.NULL_ID;


@RequestMapping("/middle/groups")
@RestController
public class GroupController {
    @Autowired
    private IGroup iG;

    @GetMapping()
    public ResponseEntity<Group[]> listAll(){
        return iG.listAllG();
    }

    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody GroupRequest group){
        try {
            if(group.getUserId()==null){
                return new ResponseEntity<>(NULL_ID, HttpStatus.BAD_REQUEST);
            }
            iG.createG(group);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idG}")
    public ResponseEntity<Group> get(@PathVariable("idG") Long idG){
        Group group = iG.readG(idG).getBody();
        if(group!=null) {return  ResponseEntity.ok(group);}
        return ResponseEntity.notFound().build();
    }

    @PutMapping()
    public ResponseEntity<Integer> update(@RequestBody Group group){
        if(group.getId()==null){
            return ResponseEntity.badRequest().body(NULL_ID);
        }
        if(iG.updateG(group).getStatusCodeValue() == HttpStatus.OK.value()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idG}")
    public ResponseEntity<Integer> delete(@PathVariable("idG") Long idG){
        if(iG.deleteG(idG).getStatusCodeValue() == HttpStatus.OK.value()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/member")
    public ResponseEntity<Integer> addM(@RequestBody GroupMemberRequest gmr){
        try{
            iG.addM(gmr);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/member")
    public ResponseEntity<Integer> deleteM(@RequestBody GroupMemberRequest gmr){
        try{
            iG.removeM(gmr);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
