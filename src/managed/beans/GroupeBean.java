package managed.beans;

import models.Contact;
import services.GroupeService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qlassalle on 01/01/2017.
 */
@ManagedBean(name = "groupe")
public class GroupeBean extends Bean {

    private String name;
    private int id;
    private List<String> lesMembres = new ArrayList<>();
    private GroupeService gs;

    @PostConstruct
    private void init() {
        gs = new GroupeService();
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParameterMap.get("idGroup") != null) {
            id = Integer.valueOf(requestParameterMap.get("idGroup"));
            List<Contact> membres = gs.getMembres(id);
            for (Contact c : membres) {
                lesMembres.add(String.valueOf(c.getId()));
            }
        }
    }

    public String createGroupe() {
        gs.save(name);
        return WELCOME_PAGE;
    }

    public String addMembre() {
        gs.AddContact(id, lesMembres);
        return WELCOME_PAGE;
    }

    public String removeFromGroupe() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        gs.removeFromGroupe((int) sessionMap.get("contactId"), id);
        return WELCOME_PAGE;
    }

    public String deleteGroupe() {
        gs.delete(id);
        return WELCOME_PAGE;
    }

    @Override
    protected boolean validate() {
        return false;
    }

    /****************************************************/

    public List<String> getLesMembres() {
        return lesMembres;
    }

    public void setLesMembres(List<String> lesMembres) {
        this.lesMembres = lesMembres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}