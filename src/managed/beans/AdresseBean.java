package managed.beans;

import helpers.Validator;
import models.Address;
import services.AddressService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Quentin on 08/12/2016.
 */
@ManagedBean(name = "adresse")
public class AdresseBean extends Bean {

    private int idAddress;
    private String street, city, zip, country;
    private AddressService as = new AddressService();

    @PostConstruct
    private void init() {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParameterMap.get("idAddress") != null) {
            Address a = as.getAddress(Integer.valueOf(requestParameterMap.get("idAddress")));
            idAddress = a.getIdAddress();
            street = a.getStreet();
            city = a.getCity();
            zip = a.getZip();
            country = a.getCountry();
        }
    }

    public String createAddress() {
        if (validate()) {
            as.addAddress(street, city, zip, country);
            return WELCOME_PAGE;
        }
        return null;
    }

    public String updateAddress() {
        if (validate()) {
            as.update(street, city, zip, country, idAddress);
            return WELCOME_PAGE;
        }
        return null;
    }

    public String deleteAddress() {
        as.delete(idAddress);
        return WELCOME_PAGE;
    }

    @Override
    protected boolean validate() {

        FacesContext context = FacesContext.getCurrentInstance();
        // load the properties file to display error messages
        ResourceBundle msgs = context.getApplication().getResourceBundle(context, "msgs");
        if (street == null || street.equals("")) {
            context.addMessage("addressForm:street", new FacesMessage(msgs.getString("creation.street.error.required")));
        }
        if (!Validator.isCorrectStreetName(getStreet())) {
            context.addMessage("addressForm:street", new FacesMessage(msgs.getString("creation.street.error.incorrect")));
        }
        if (city == null || city.equals("")) {
            context.addMessage("addressForm:city", new FacesMessage(msgs.getString("creation.city.error.required")));
        }
        if (!Validator.isCorrectString(getCity())) {
            context.addMessage("addressForm:city", new FacesMessage(msgs.getString("creation.city.error.incorrect")));
        }
        if (country == null || country.equals("")) {
            context.addMessage("addressForm:country", new FacesMessage(msgs.getString("creation.country.error.required")));
        }
        if (!Validator.isCorrectString(getCountry())) {
            context.addMessage("addressForm:country", new FacesMessage(msgs.getString("creation.country.error.incorrect")));
        }
        if (zip == null || zip.length() > 5) {
            context.addMessage("addressForm:zip", new FacesMessage(msgs.getString("creation.zip.error.required")));
        }
        if (!Validator.isCorrectNumber(getZip())) {
            context.addMessage("addressForm:zip", new FacesMessage(msgs.getString("creation.zip.error.incorrect")));
        }
        return context.getMessageList().isEmpty();
    }

    /***********************************************************************/

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
