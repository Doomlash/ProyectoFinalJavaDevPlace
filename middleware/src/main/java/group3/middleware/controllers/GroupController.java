package group3.middleware.controllers;

import group3.middleware.model.Group;
import group3.middleware.model.GroupMemberRequest;
import group3.middleware.services.implementation.IGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/middle/groups")
@RestController
public class GroupController {
    @Autowired
    private IGroup iG;

    @GetMapping()
    public ResponseEntity<List<Group>> listAll(){
        return iG.listAllG();
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Group group){
        System.out.println("pase");
        return iG.createG(group);
    }

    @GetMapping("/{idG}")
    public ResponseEntity<Group> get(@PathVariable("idG") Long idG){
        return iG.readG(idG);
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Group group){
        return iG.updateG(group);
    }

    @DeleteMapping("/{idG}")
    public ResponseEntity<String> delete(@PathVariable("idG") Long idG){
        return iG.deleteG(idG);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/member")
    public ResponseEntity<String> addM(@RequestBody GroupMemberRequest gmr){
        return iG.addM(gmr);
    }

    @DeleteMapping("/member")
    public ResponseEntity<String> deleteM(@RequestBody GroupMemberRequest gmr){
        return iG.removeM(gmr);
    }

}
