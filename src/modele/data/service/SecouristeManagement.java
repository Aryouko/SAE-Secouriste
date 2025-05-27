package modele.data.service;
import modele.data.persistence.Secouriste;
import java.util.List;

public class SecouristeManagement {

    List<Secouriste> secouristes;


    public void ajouterSecouriste(String nom, String prenom, String dateNaissance, String email, String tel, String adresse) {
        Secouriste secouriste = new Secouriste(String nom, String prenom, String dateNaissance, String email, String tel, String adresse);
    }
}