package managed.beans;

/**
 * Created by qlassalle on 31/12/2016.
 */

import models.Address;
import models.Contact;
import models.Groupe;
import services.AddressService;
import services.ContactService;
import services.GroupeService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "accueil")
public class AccueilBean {

    private List<Contact> lesContacts = new ArrayList<>();
    private List<Address> lesAdresses = new ArrayList<>();
    private List<Map.Entry<Groupe, Integer>> lesGroupes;
    private Map<Groupe, Integer> infosGroupe = new HashMap<>();
    private ContactService cs = new ContactService();
    private AddressService as = new AddressService();
    private GroupeService gs = new GroupeService();

    @PostConstruct
    public void init() {
        System.out.println("called");
        lesContacts = cs.getAllContacts();
        lesAdresses = as.getAllAddresses();
        for (Groupe g : gs.getAllGroupes()) {
            infosGroupe.put(g, gs.getNbMembre(g.getId()));
        }
        lesGroupes = new ArrayList<>(infosGroupe.entrySet());
    }

    public List<Contact> getLesContacts() {
        return lesContacts;
    }

    public List<Address> getLesAdresses() {
        return lesAdresses;
    }

    public List<Map.Entry<Groupe, Integer>> getLesGroupes() {
        return lesGroupes;
    }
}