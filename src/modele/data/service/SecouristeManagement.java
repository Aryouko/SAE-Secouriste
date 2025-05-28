package modele.data.service;
import modele.data.persistence.Secouriste;
import java.util.List;

public class SecouristeManagement {

    List<Secouriste> secouristes;

    /**
     * Constructor of SecouristeManagement
     */
    public void ajouterSecouriste(long id, String nom, String prenom, String dateNaissance, String email, String tel, String adresse) {
        Secouriste secouriste = new Secouriste(id, nom, prenom, dateNaissance, email, tel, adresse);
    }

    /**
     * Get the list of secouristes
     * @return the list of secouristes
     */
    public List<Secouriste> getSecouristes() {
        return secouristes;
    }


}