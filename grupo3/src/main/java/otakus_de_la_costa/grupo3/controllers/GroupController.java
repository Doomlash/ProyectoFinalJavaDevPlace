package otakus_de_la_costa.grupo3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import otakus_de_la_costa.grupo3.services.IGroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private IGroupService gService;


	//CREATE ONE GROUP
	@PostMapping()
	public ResponseEntity<String> createGroup(@RequestBody Group group){
		group.setId(null);
		group.setActive(true);
		gService.createGroup(group);
		return new ResponseEntity<>("group created",HttpStatus.CREATED);
	}

	@PostMapping("/member")
	public ResponseEntity<String> addMember(@RequestBody GroupMemberRequest request){
		gService.addMember(request);
		return new ResponseEntity<>("member added",HttpStatus.CREATED);
	}

	@PutMapping("/member")
	public ResponseEntity<String> removeMember(@RequestBody GroupMemberRequest request){
		gService.deleteMember(request);
		return new ResponseEntity<>("member removed",HttpStatus.CREATED);
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
	public ResponseEntity<String> updateGroup(@RequestBody Group group){
		if(group.getId()==null){
			return ResponseEntity.badRequest().body("la id debe contener una id valida");
		}
		if(gService.updateGroup(group)){
			return new ResponseEntity<>("grupo actualizado",HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>("el grupo no existe",HttpStatus.BAD_REQUEST);
		}
	}

	//DELETE Group
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable(value = "id")Long id){
		if(gService.deleteGroup(id)) {
			return new ResponseEntity<>("Grupo eliminado con exito",HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
}