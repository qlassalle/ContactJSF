package managed.beans;

import models.PhoneNumber;
import services.PhoneNumberService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Created by qlassalle on 02/01/2017.
 */
@ManagedBean(name = "phone")
public class PhoneNumberBean extends Bean {

    // id represents idContact here but I had to name it id to map with contact.id
    private int idPhoneNumber, id;
    private String kind, number;
    private PhoneNumberService pns;

    @PostConstruct
    public void init() {
        pns = new PhoneNumberService();
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParameterMap.get("idPhoneNumber") != null) {
            idPhoneNumber = Integer.valueOf(requestParameterMap.get("idPhoneNumber"));
            PhoneNumber phone = pns.getPhoneNumber(idPhoneNumber);
            idPhoneNumber = phone.getIdPhoneNumber();
            kind = phone.getKind();
            number = phone.getNumber();
        }

        if (requestParameterMap.get("id") != null) {
            id = Integer.valueOf(requestParameterMap.get("id"));
        }
    }

    public String createPhoneNumber() {
        pns.save(kind, number, id);
        return WELCOME_PAGE;
    }

    public String deletePhoneNumber() {
        pns.delete(idPhoneNumber);
        return WELCOME_PAGE;
    }

    public String update() {
        pns.update(idPhoneNumber, kind, number);
        return WELCOME_PAGE;
    }

    @Override
    protected boolean validate() {
        return false;
    }

    /******************************************/

    public int getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(int idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
