package otakus_de_la_costa.grupo3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import otakus_de_la_costa.grupo3.model.Contact;
import otakus_de_la_costa.grupo3.services.IContact;

@RestController
@RequestMapping("/api/")
public class ContactController {
	@Autowired
	private IContact cService;
	
	@PostMapping("/users/{idUser}/addContact/{idContact}")
	public ResponseEntity<Contact> addContact(@PathVariable(value = "idUser")Long idUser,@PathVariable(value = "idContact")Long idContact){
		return new ResponseEntity<>(cService.createContact(idUser,idContact),HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{idUser}/listContacts")
	public List<Contact> listContacts(@PathVariable (value = "idUser") Long idUser){
		return cService.viewAllContacts(idUser);
	}
	
	@GetMapping("/users/{idUser}/contacts/{id}")
	public ResponseEntity<Contact> viewContact(@PathVariable(value = "idUser")Long idUser,@PathVariable(value = "id")Long contactId){
		Contact contact =cService.findContactId(idUser, contactId);
		if (contact ==null) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<>(contact,HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{idUser}/contacts/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable(value = "idUser")Long idUser,@PathVariable(value = "id")Long contactId){
		cService.deleteContact(idUser, contactId);
		return new ResponseEntity<>("Contacto eliminado",HttpStatus.OK);
	}
}
