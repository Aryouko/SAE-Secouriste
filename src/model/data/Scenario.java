package model.data;

import model.data.persistence.DPS;
import model.data.persistence.Journee;
import model.data.persistence.Sport;
import model.data.service.AuthentificationManagement;
import model.data.service.DPSManagement;

public class Scenario {

    private static final DPSManagement dpsManagement = new DPSManagement();


    public static void main(String[] args) {

        System.out.println("Scenario class is ready to be implemented.");

        // L'admin crée un nouveau dps.

        // 1. Il se connecte à l'application.
        // 2. Il accède à la page de gestion des DPS.
        // 3. Il clique sur le bouton "Créer un nouveau DPS".
        // 4. Il remplit les informations nécessaires (nom, date, lieu, etc.).
        // 5. Il enregistre le DPS.
        // 6. Il peut voir le nouveau DPS dans la liste des DPS.


        // 1. Connexion à l'application en tant qu'admin
/*
        AuthentificationManagement auth = AuthentificationManagement.getInstanceAuthentificationManagement();
        AuthentificationManagement.LoginResult result = auth.login("admin@mail.com", "motdepasseAdmin");
        if (result != AuthentificationManagement.LoginResult.SUCCESS || !auth.isAdmin()) {
            System.out.println("Connexion admin échouée !");
        }

        System.out.println("Admin connecté.");
        long id = this.dpsManagement.numberOfDps() + 1;

        Journee journee = new Journee(10, 10, 10);
        newDps = new DPS(id, this.nomTextField.getText(), this.horaireDeb, this.horaireFin, this.site, this.sport, journee);




        insert
        launchAffectation

        // 2. Il accède à la page de gestion des DPS.
        DPSManagement dpsManagement = new DPSManagement();
        DPS nouveauDps = new DPS("NomDPS", "Lieu", "2025-07-01", ); // complète les champs nécessaires

        FenetreAjoutDPSController fenetreAjoutDPSController = new FenetreAjoutDPSController();
        fenetreAjoutDPSController.ajoutDPS();

        boolean creation = dpsManagement.addDPS(nouveauDps);



        if (creation) {
            System.out.println("Nouveau DPS créé : " + nouveauDps.getName());
        } else {
            System.out.println("Erreur lors de la création du DPS.");
        }

 */



    }
}
