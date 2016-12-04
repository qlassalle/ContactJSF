import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/*annotation qui déclare le Bean à JSF.
Cela évite de le faire dans le fichier faces-config.xml
il est possible de rajouter l'attribut "name" afin de donner un nom d'alias au Bean. exp @ManagedBean(name="control")
Il sera référencé dans le form login.xhtml à travers ce nom d'alias.
Autrement, il sera référencé par controlAccess (le c en miniscule)*/

@ManagedBean(name = "control")
@SessionScoped
public class ControlAccess implements Serializable {

    //login et password du User
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Methode Action Controller qui sera référencée dans le formulaire
    //de la page login.xhtml et appelée par JSF lors de la soumission du formulaire
    public String checkAccess(){

        //Contrôle que les champs sont non-vides
        if (isMissing(login)||isMissing(password)) {
            //si les champs ne son pas renseignés,
            //JSF forwadera l'execution vers la page missing-login-pass.xhtml
            return("missing-login-pass");

        } else if (login.equals(password))
        {
            //si le pass est correct,
            //JSF forwadera l'execution vers la page contactDisplay.xhtml
            return("welcome-page");
        } else {
            //Sinon, vers la page bad-login.xhtml
            return("bad-login");
        }

    }
    //Contrôle que les champs sont non-vides
    private boolean isMissing(String value) {
        return((value == null) || (value.trim().isEmpty()));
    }
}
