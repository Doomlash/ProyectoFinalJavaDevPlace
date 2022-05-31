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

import group3.middleware.model.Group;
import group3.middleware.model.request.GroupMemberRequest;
import group3.middleware.model.request.GroupRequest;
import group3.middleware.services.implementation.IGroup;


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
        return iG.createG(group);
    }

    @GetMapping("/{idG}")
    public ResponseEntity<Group> get(@PathVariable("idG") Long idG){
        return iG.readG(idG);
    }

    @PutMapping()
    public ResponseEntity<Integer> update(@RequestBody Group group){
        return iG.updateG(group);
    }

    @DeleteMapping("/{idG}")
    public ResponseEntity<Integer> delete(@PathVariable("idG") Long idG){
       return iG.deleteG(idG);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/member")
    public ResponseEntity<Integer> addM(@RequestBody GroupMemberRequest gmr){
        return iG.addM(gmr);
    }

    @PutMapping("/member")
    public ResponseEntity<Integer> removeM(@RequestBody GroupMemberRequest gmr){
       return iG.removeM(gmr);
    }

    @GetMapping("/{idG}/isAdmin/{idU}")
    public ResponseEntity<Object> isAdmin(@PathVariable("idG") Long idG,@PathVariable("idU") Long idU){
        return iG.isAdmin(idG,idU);
    }

    @PutMapping("/{idG}/changeAdmin/{idU}")
    public ResponseEntity<Object> changeAdmin(@PathVariable("idG") Long idG,@PathVariable("idU") Long idU){
        return iG.changeAdmin(idG,idU);
    }
}
