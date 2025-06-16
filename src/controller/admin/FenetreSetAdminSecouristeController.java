package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.data.persistence.*;
import model.data.service.*;

import static controller.admin.FenetreGestionController.removeOverlay;

public class FenetreSetAdminSecouristeController {

    @FXML
    private AnchorPane rootPane;

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private final PossessionManagement possessionManagement = new PossessionManagement();

    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();

    private final AdministrateurManagement administrateurManagement = new AdministrateurManagement();

    private Secouriste secouriste;

    private GestionSecouristeController gestionSecouristeController;

    public void initializeSetAdminSecouriste(Secouriste secouriste, GestionSecouristeController gestionSecouristeController) {
        this.secouriste = secouriste;
        this.gestionSecouristeController = gestionSecouristeController;
    }

    @FXML
    public void setAdmin() {
        Administrateur newAdmin = new Administrateur(this.secouriste.getIdSecouriste(), this.secouriste.getNom(), this.secouriste.getPrenom(), this.secouriste.getDateNaissance(), this.secouriste.getTel(), this.secouriste.getAdresse());
        this.administrateurManagement.addAdministrateur(newAdmin);

        for (Affectation affectation : this.affectationManagement.getAffectationsByRescuer(this.secouriste)) {
            this.affectationManagement.removeAffectation(affectation);
        }

        for (Disponibilite disponibilite : this.disponibiliteManagement.getDisponibilites(this.secouriste)) {
            this.disponibiliteManagement.removeDisponibilite(this.secouriste, disponibilite.getJourDisp());
        }

        for (Competence competence : this.possessionManagement.getPossessionBySecouriste(this.secouriste).getCompetencesSec()) {
            this.possessionManagement.removePossession(secouriste, competence);
        }
        this.secouristeManagement.removeSecouriste(this.secouriste);
        this.gestionSecouristeController.retirerList(secouriste);
        this.gestionSecouristeController.filtreUpdate();
        annuleAdmin();
    }

    @FXML
    public void annuleAdmin() {
        removeOverlay();
        rootPane.getChildren().removeAll();
    }
}
