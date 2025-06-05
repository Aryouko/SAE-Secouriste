package model.data.service;
import model.dao.DAOFactory;
import model.data.persistence.Secouriste;

import java.util.ArrayList;
import java.util.List;

public class SecouristeManagement {

    List<Secouriste> secouristes;

    /**
     * Constructor of SecouristeManagement
     */
    public void ajouterSecouriste(long id, String nom, String prenom, String dateNaissance, String tel, String adresse) {
        this.secouristes.add(new Secouriste(id, nom, prenom, dateNaissance, tel, adresse));
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