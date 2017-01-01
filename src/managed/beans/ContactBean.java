package managed.beans;

import models.Contact;
import services.ContactService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Quentin on 21/11/2016.
 */

@ManagedBean(name="contact")
public class ContactBean extends Bean{

    private String firstName, lastName, email;
    private int id, idAddress;
    private ContactService cs;

    @PostConstruct
    private void init() {
        cs = new ContactService();
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParameterMap.get("idContact") != null) {
            Contact c = cs.getContactById(Integer.valueOf(requestParameterMap.get("idContact")));
            id = (int)c.getId();
            firstName = c.getFirstName();
            lastName = c.getLastName();
            email = c.getEmail();
            try {
                c.setAddress(cs.getContactAddress((int) c.getId()));
                idAddress = c.getAddress().getIdAddress();
            } catch (NullPointerException npe) {
                idAddress = 0;
            }
        }
    }

    public String createContact() {
        if(validate()) {
            cs.addContact(lastName, firstName, email);
            return "/welcome-page";
        }
        return null;
    }

    public String updateContact() {
        if(validate()) {
            cs.update(id, lastName, firstName, email);
            return "updateContact";
        }
        return null;
    }

    public String deleteContact() {
        cs.delete(id);
        return "welcome-page";
    }

    public String selectAddress() {
        cs.addAddress(id, idAddress);
        return "/welcome-page";
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
}
