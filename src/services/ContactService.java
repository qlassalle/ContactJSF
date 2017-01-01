package services;

import daos.DAOContact;
import models.Address;
import models.Contact;

import java.util.List;
import java.util.Map;

public class ContactService {

	DAOContact daoc;
	
	public ContactService() {
		daoc = new DAOContact();
	}

	public String addContact(String nom, String prenom, String email) {
		return daoc.save(nom, prenom, email);
	}
	
	public String update(int id, String nom, String prenom, String email) {
		return daoc.update(id, nom, prenom, email);
	}

	public String delete(int id) {
		return daoc.delete(id);
	}
	
	public List<Contact> getContactByFirstName(String firstName) {
		return daoc.getContactByFirstName(firstName);
	}
	
	public List<Contact> getAllContacts() {
		return daoc.getAllContacts();
	}
	
	public String addAddress(int idContact, int idAddress){
		return daoc.addAddress(idContact, idAddress);
	}
	
	public Map<Integer, String> getGroupes(int idContact) {
		return daoc.getGroupes(idContact);
	}
	
	public Contact getContactById(int id) {
		return daoc.getContactById(id);
	}
	
	public Address getContactAddress(int idContact) {
		return daoc.getContactAddress(idContact);
	}
}
