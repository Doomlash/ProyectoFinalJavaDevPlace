package otakus_de_la_costa.grupo3.controllers;

import java.util.List;
import java.util.Optional;

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

import otakus_de_la_costa.grupo3.database.GroupJPA;
import otakus_de_la_costa.grupo3.services.GroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private GroupService gService;
	
	
	//CREATE GROUP
	@PostMapping("/addGroup")
	public ResponseEntity<?> createGroup(@RequestBody GroupJPA group ){
		return ResponseEntity.status(HttpStatus.CREATED).body(gService.save(group));
	}
	
	//READ GROUP
	@GetMapping("/{id}")
	public ResponseEntity<?> readGroup(@PathVariable (value = "id") Long id){
		Optional<GroupJPA> group= gService.findById(id);
		if (!group.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(group);

	}
	
	//READ ALL GROUP
	@GetMapping("/allGroups")
	public List<GroupJPA> readAll(){
		return gService.findAll();
	}
	
	//EDIT GROUP
	
	@PutMapping("/editGroup/{id}")
	public ResponseEntity<?> editGroup(@PathVariable (value = "id") Long idGroup,@RequestBody GroupJPA myGroup){
		Optional<GroupJPA> group= gService.findById(idGroup);
		if(!group.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		group.get().setName(myGroup.getName());
		group.get().setDescription(myGroup.getDescription());
		return ResponseEntity.status(HttpStatus.CREATED).body(gService.save(group.get()));
	}
	
	//DELETE GROUP
	
	@DeleteMapping("/deleteGroup({id}")
	public ResponseEntity<?> deleteGroup(@PathVariable (value = "id") Long id){
		if(!gService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		gService.delete(id);
		return ResponseEntity.ok().build();
	}
}
