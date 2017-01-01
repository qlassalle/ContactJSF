package services;

import daos.DAOGroupe;
import models.Contact;
import models.Groupe;

import java.util.List;

public class GroupeService {

	
	DAOGroupe daog;

	public GroupeService() {
		daog = new DAOGroupe();
	}
	
	public String save(String name) {
		return daog.save(name);
	}
	
	public String delete(int idGroupe) {
		return daog.delete(idGroupe);
	}
	
	public List<Groupe> getAllGroupes() {
		return daog.getAllGroupes();
	}
	
	public int getNbMembre(int id) {
		return daog.getNbMembre(id);
	}

	public void AddContact(int id, List<String> ids) {
		daog.AddContact(id, ids);
	}
	
	public List<Contact> getMembres(int idGroupe) {
		return daog.getMembres(idGroupe);
	}
	
	public String removeFromGroupe(int idContact, int idGroupe) {
		return daog.removeFromGroupe(idContact, idGroupe);
	}
}
