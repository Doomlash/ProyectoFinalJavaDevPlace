package otakus_de_la_costa.grupo3.controllers;

import static otakus_de_la_costa.grupo3.model.Constants.IDS_NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.NOT_FOUND;
import static otakus_de_la_costa.grupo3.model.Constants.NOT_MEMBER;
import static otakus_de_la_costa.grupo3.model.Constants.NULL_ID;
import static otakus_de_la_costa.grupo3.model.Constants.OK;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.GroupMemberRequest;
import otakus_de_la_costa.grupo3.model.GroupRequest;
import otakus_de_la_costa.grupo3.services.IGroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private IGroupService gService;
	
	
	//CREATE ONE GROUP
    @PostMapping()
    public ResponseEntity<Integer> createGroup(@RequestBody GroupRequest group){
        try {
            if(group.getUserId()==null){
                return new ResponseEntity<>(NULL_ID,HttpStatus.BAD_REQUEST);
            }
            gService.createGroup(group);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/member")
    public ResponseEntity<Integer> addMember(@RequestBody GroupMemberRequest request){
        try{
            gService.addMember(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (JpaSystemException e){
            if (e.getRootCause().getClass()==SQLException.class) {
                return new ResponseEntity<>(Integer.valueOf(((SQLException) e.getRootCause()).getSQLState()),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>(((SQLException) e.getRootCause()).getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/member")
    public ResponseEntity<Integer> removeMember(@RequestBody GroupMemberRequest request){
        try{
            Integer response = gService.deleteMember(request);
            if(response == OK){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (JpaSystemException e){
            if (e.getRootCause().getClass()==SQLException.class) {
                return new ResponseEntity<>(Integer.valueOf(((SQLException) e.getRootCause()).getSQLState()),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //List groups
    @GetMapping()
    public ResponseEntity<List<Group>> listGroups(){
        return ResponseEntity.ok(gService.listAllGroups());
    }
    //READ Group
    @GetMapping("/{id}")
    public ResponseEntity<Group> readgroup(@PathVariable (value = "id") Long id){
        Group group=gService.findGroupById(id);
        if(group!=null) {return  ResponseEntity.ok(gService.findGroupById(id));}
        return ResponseEntity.notFound().build();
    }
		
    //UPDATE Group
    @PutMapping()
    public ResponseEntity<Integer> updateGroup(@RequestBody Group group){
        if(group.getId()==null){
            return ResponseEntity.badRequest().body(NULL_ID);
        }
        if(gService.updateGroup(group)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }
		
    //DELETE Group
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteGroup(@PathVariable(value = "id")Long id){
        if(gService.deleteGroup(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{group}/isAdmin/{user}")
    public ResponseEntity<Object> isAdmin(@PathVariable(value = "group")Long group,@PathVariable(value = "user")Long user){
        try{
            Boolean response = gService.isAdmin(group,user);
            if (response!=null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NOT_MEMBER,HttpStatus.BAD_REQUEST);
            }
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(IDS_NOT_FOUND,HttpStatus.BAD_REQUEST);
        }               
    }

    @PutMapping("/{group}/changeAdmin/{user}")
    public ResponseEntity<Object> changeAdmin(@PathVariable(value = "group")Long group,@PathVariable(value = "user")Long user){
        try{
            Integer response = gService.changeAdmin(group,user);
            if (response==OK) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }catch (JpaSystemException e){
            if (e.getRootCause().getClass()==SQLException.class) {
                return new ResponseEntity<>(Integer.valueOf(((SQLException) e.getRootCause()).getSQLState()),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

}
