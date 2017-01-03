package managed.beans;

import models.Address;
import models.Contact;
import models.Groupe;
import models.PhoneNumber;
import services.ContactService;
import services.PhoneNumberService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Quentin on 21/11/2016.
 */

@ManagedBean(name="contact")
@ViewScoped
public class ContactBean extends Bean{

    private String firstName, lastName, email;
    private int id, idAddress;
    private Address address;
    private List<PhoneNumber> phone;
    private List<Groupe> books;
    private ContactService cs;

    @PostConstruct
    private void init() {
        cs = new ContactService();
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        if (requestParameterMap.get("idContact") != null) {
            Contact c = cs.getContactById(Integer.valueOf(requestParameterMap.get("idContact")));
            id = (int)c.getId();
            firstName = c.getFirstName();
            lastName = c.getLastName();
            email = c.getEmail();
            getAddress(c);
            getPhoneNumber(c);
            books = cs.getGroupeList(id);
            sessionMap.put("contactId", id);
        }
    }

    private void getPhoneNumber(Contact c) {
        try {
            PhoneNumberService pns = new PhoneNumberService();
            phone = pns.getPhoneNumbers(id);
        } catch (NullPointerException npe) {
            phone = null;
        }
    }

    private void getAddress(Contact c) {
        try {
            address = cs.getContactAddress(id);
            c.setAddress(cs.getContactAddress((int) c.getId()));
            idAddress = c.getAddress().getIdAddress();
        } catch (NullPointerException npe) {
            address = null;
            idAddress = 0;
        }
    }

    public String createContact() {
        if(validate()) {
            cs.addContact(lastName, firstName, email);
            return WELCOME_PAGE;
        }
        return null;
    }

    public String updateContact() {
        if(validate()) {
            cs.update(id, lastName, firstName, email);
            return WELCOME_PAGE;
        }
        return null;
    }

    public String deleteContact() {
        cs.delete(id);
        return WELCOME_PAGE;
    }

    public String selectAddress() {
        cs.addAddress(id, idAddress);
        return WELCOME_PAGE;
    }

    public String search() {
        System.out.println(firstName);
        return WELCOME_PAGE;
    }

    @Override
    protected boolean validate() {
        FacesContext context = FacesContext.getCurrentInstance();
        // load the properties file to display error messages
        ResourceBundle msgs = context.getApplication().getResourceBundle(context, "msgs");
        if(lastName == null || lastName.equals("")) {
            context.addMessage(null, new FacesMessage(msgs.getString("creation.ln.error.required")));
        }
        if(firstName == null || firstName.equals("")) {
            context.addMessage(null, new FacesMessage(msgs.getString("creation.fn.error.required")));
        }
        if(email == null || email.equals("")) {
            context.addMessage(null, new FacesMessage(msgs.getString("creation.email.error.required")));
        }
        return context.getMessageList().size() == 0;
    }

    /****************************************************/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PhoneNumber> getPhone() {
        return phone;
    }

    public void setPhone(List<PhoneNumber> phone) {
        this.phone = phone;
    }

    public List<Groupe> getBooks() {
        return books;
    }

    public void setBooks(List<Groupe> books) {
        this.books = books;
    }
}
