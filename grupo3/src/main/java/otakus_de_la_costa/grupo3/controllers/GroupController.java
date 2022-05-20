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
import otakus_de_la_costa.grupo3.model.Group;
import otakus_de_la_costa.grupo3.model.MyUser;
import otakus_de_la_costa.grupo3.services.GroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private GroupService gService;
	
	
	//CREATE ONE GROUP
		@PostMapping("/addGroup")
		public ResponseEntity<Group> createGroup(@RequestBody Group group){
			return new ResponseEntity<>(gService.createGroup(group),HttpStatus.CREATED);
		}
		//List groups
		@GetMapping("/listGroups")
		public List<Group> listGroups(){
			return gService.listAllGroups();
		}
		//READ Group
		@GetMapping("/{id}")
		public ResponseEntity<Group> readgroup(@PathVariable (value = "id") Long id){
			Group group=gService.findGroupById(id);
			if(group!=null) {return  ResponseEntity.ok(gService.findGroupById(id));}
			return ResponseEntity.notFound().build();
		}
		
		//UPDATE Group
		@PutMapping("/{id}")
		public ResponseEntity<Group> updateGroup(@PathVariable (value = "id")Long id,@RequestBody Group group){
			Group myNewGroup=gService.updateGroup(group, id);
			return new ResponseEntity<>(myNewGroup,HttpStatus.OK);
		}
		
		//DELETE Group
		@DeleteMapping("/{id}")
		public ResponseEntity<String> deleteGroup(@PathVariable(value = "id")Long id){
			boolean delete=gService.deleteGroup(id);
			if(delete) {
			return new ResponseEntity<>("Grupo eliminado con exito",HttpStatus.OK);}
			return ResponseEntity.notFound().build();
		}
}
