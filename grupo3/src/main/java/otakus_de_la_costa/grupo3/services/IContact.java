package otakus_de_la_costa.grupo3.services;

import java.util.List;

import otakus_de_la_costa.grupo3.model.Contact;

public interface IContact {
	public Contact createContact(Long userId,Long contId);
	public List<Contact> viewAllContacts(Long userId);
	public Contact findContactId(Long userId,Long contactId);
	public void deleteContact(Long userId,Long contactId);
}
