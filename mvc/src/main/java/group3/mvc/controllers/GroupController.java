package group3.mvc.controllers;


import group3.mvc.model.Group;
import group3.mvc.model.request.GroupMemberRequest;
import group3.mvc.model.request.GroupRequest;
import group3.mvc.services.implementation.IGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/mvc/groups")
@RestController
public class GroupController {
    @Autowired
    private IGroup iG;

    @GetMapping()
    public List<Group> listAll(){
        return iG.listAllG();
    }

    @PostMapping()
    public Integer create(@RequestBody GroupRequest group){
        return iG.createG(group);
    }

    @GetMapping("/{idG}")
    public Group get(@PathVariable("idG") Long idG){
        return iG.readG(idG);
    }

    @PutMapping()
    public Integer update(@RequestBody Group group){
        return iG.updateG(group);
    }

    @DeleteMapping("/{idG}")
    public Integer delete(@PathVariable("idG") Long idG){
        return iG.deleteG(idG);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/member")
    public Integer addM(@RequestBody GroupMemberRequest gmr){
        return iG.addM(gmr);
    }

    @PutMapping("/member")
    public Integer removeM(@RequestBody GroupMemberRequest gmr){
        return iG.removeM(gmr);
    }

    @GetMapping("/{idG}/isAdmin/{idU}")
    public Object isAdmin(@PathVariable("idG") Long idG,@PathVariable("idU") Long idU){
        return iG.isAdmin(idG,idU);
    }

    @PutMapping("/{idG}/changeAdmin/{idU}")
    public Object changeAdmin(@PathVariable("idG") Long idG,@PathVariable("idU") Long idU){
        return iG.changeAdmin(idG,idU);
    }


}
