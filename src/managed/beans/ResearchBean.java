package managed.beans;

import models.Contact;
import services.ContactService;

import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * Created by qlassalle on 03/01/2017.
 */
@ManagedBean(name = "research")
public class ResearchBean extends Bean {

    private final static String SEARCH_PAGE = "contact/searchedContacts";
    List<Contact> lesContacts;
    private String firstName;
    private ContactService cs = new ContactService();

    public String search() {
        lesContacts = cs.getContactByFirstName(firstName);
        return SEARCH_PAGE;
    }

    @Override
    protected boolean validate() {
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Contact> getLesContacts() {
        return lesContacts;
    }

    public void setLesContacts(List<Contact> lesContacts) {
        this.lesContacts = lesContacts;
    }
}