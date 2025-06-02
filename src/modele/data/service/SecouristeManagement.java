package modele.data.service;
import modele.dao.DAOFactory;
import modele.data.persistence.Secouriste;

import java.util.ArrayList;
import java.util.List;

public class SecouristeManagement {

    List<Secouriste> secouristes;

    /**
     * Constructor of SecouristeManagement
     */
    public void ajouterSecouriste(long id, String pseudo, String nom, String prenom, String dateNaissance, String email, String tel, String adresse) {
        Secouriste secouriste = new Secouriste(id, pseudo, nom, prenom, dateNaissance, email, tel, adresse);
    }

    /**
     * Get the list of secouristes
     * @return the list of secouristes
     */
    public List<Secouriste> getSecouristes() {
        return secouristes;
    }


    public List<Secouriste> getAllSecouristes() {
        return DAOFactory.getSecouristeDAO().findAll();
    }

    /**
     * Cherche un Sauveteur en particulier
     *
     * @param nomRech name of the sauveteur to find
     * @return a liste of sauveteur
     */
    public List<Secouriste> chercherParNom(String nomRech) {
        List<Secouriste> tous = getAllSecouristes();
        List<Secouriste> resultat = new ArrayList<>();
        for (Secouriste s : tous) {
            if (s.getNom().equalsIgnoreCase(nomRech)) {
                resultat.add(s);
            }
        }
        return resultat;
    }
}