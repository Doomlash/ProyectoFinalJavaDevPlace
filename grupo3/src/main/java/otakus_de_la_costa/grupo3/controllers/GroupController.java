package otakus_de_la_costa.grupo3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.services.IGroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private IGroupService gService;
	
	
	// //CREATE ONE GROUP
	// 	@PostMapping("/addGroup")
	// 	public ResponseEntity<Group> createGroup(@RequestBody Group group){
	// 		return new ResponseEntity<>(gService.createGroup(group),HttpStatus.CREATED);
	// 	}
	// 	//List groups
	// 	@GetMapping("/listGroups")
	// 	public List<Group> listGroups(){
	// 		return gService.listAllGroups();
	// 	}
	// 	//READ Group
	// 	@GetMapping("/{id}")
	// 	public ResponseEntity<Group> readgroup(@PathVariable (value = "id") Long id){
	// 		Group group=gService.findGroupById(id);
	// 		if(group!=null) {return  ResponseEntity.ok(gService.findGroupById(id));}
	// 		return ResponseEntity.notFound().build();
	// 	}
		
	// 	//UPDATE Group
	// 	@PutMapping("/{id}")
	// 	public ResponseEntity<Group> updateGroup(@PathVariable (value = "id")Long id,@RequestBody Group group){
	// 		Group myNewGroup=gService.updateGroup(group, id);
	// 		return new ResponseEntity<>(myNewGroup,HttpStatus.OK);
	// 	}
		
	// 	//DELETE Group
	// 	@DeleteMapping("/{id}")
	// 	public ResponseEntity<String> deleteGroup(@PathVariable(value = "id")Long id){
	// 		boolean delete=gService.deleteGroup(id);
	// 		if(delete) {
	// 		return new ResponseEntity<>("Grupo eliminado con exito",HttpStatus.OK);}
	// 		return ResponseEntity.notFound().build();
	// 	}
}
