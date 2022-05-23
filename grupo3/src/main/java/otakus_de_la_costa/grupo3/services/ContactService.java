package otakus_de_la_costa.grupo3.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otakus_de_la_costa.grupo3.database.ContactJPA;
import otakus_de_la_costa.grupo3.database.MyUserJPA;
import otakus_de_la_costa.grupo3.model.Contact;
import otakus_de_la_costa.grupo3.repositories.ContactRepository;
import otakus_de_la_costa.grupo3.repositories.UserRepository;

@Service
public class ContactService implements IContact {
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private UserRepository uRepo;
	@Override
	public Contact createContact(Long userId,Long contId) {
		Optional<MyUserJPA> myUser=uRepo.findById(userId);
		Optional<MyUserJPA> myContact=uRepo.findById(contId);
		ContactJPA conJPA= mapearContactJPA(myContact.get());
		conJPA.setMyUser(myUser.get());
		ContactJPA newContact = contactRepo.save(conJPA);
		
		return mapearContact(newContact);
	}
	
	private Contact mapearContact(ContactJPA contJPA) {
		Contact contact= new Contact();
		contact.setId(contJPA.getId());
		contact.setUsername(contJPA.getUsername());
		contact.setEmail(contJPA.getEmail());
		return contact;
	}
	
	private ContactJPA mapearContactJPA(MyUserJPA myUser) {
		ContactJPA conJPA = new ContactJPA();
		conJPA.setId(myUser.getId());
		conJPA.setUsername(myUser.getUsername());
		conJPA.setEmail(myUser.getMail());
		return conJPA;
	}

	@Override
	public List<Contact> viewAllContacts(Long userId) {
		List<ContactJPA> contacts = contactRepo.findByMyUserId(userId);
		return contacts.stream().map(contactJPA -> mapearContact(contactJPA)).collect(Collectors.toList());
	}

	@Override
	public Contact findContactId(Long userId, Long contactId) {
		Optional<MyUserJPA> myUser=uRepo.findById(userId);
		Optional<ContactJPA> contactJPA= contactRepo.findById(contactId);
		//posible exception (implementar)
		if(!contactJPA.get().getMyUser().getId().equals(myUser.get().getId())) {
			return null;
		}
		return mapearContact(contactJPA.get());
	}

	@Override
	public void deleteContact(Long userId, Long contactId) {
		Optional<MyUserJPA> myUser=uRepo.findById(userId);
		Optional<ContactJPA> contactJPA= contactRepo.findById(contactId);
		contactRepo.delete(contactJPA.get());
		
	}
}
