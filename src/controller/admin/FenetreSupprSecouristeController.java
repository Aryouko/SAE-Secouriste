package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.data.persistence.*;
import model.data.service.*;

import static controller.admin.FenetreGestionController.removeOverlay;
import static controller.admin.FenetreGestionController.showOverlay;

public class FenetreSupprSecouristeController {

    @FXML
    private AnchorPane rootPane;

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private final PossessionManagement possessionManagement = new PossessionManagement();

    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();

    private Secouriste secouriste;

    private GestionSecouristeController gestionSecouristeController;

    public void initializeSecouriste(Secouriste secouriste, GestionSecouristeController gestionSecouristeController) {
        this.secouriste = secouriste;
        this.gestionSecouristeController = gestionSecouristeController;
    }

    @FXML
    public void supprSecouriste() {
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
        annuleSecouriste();
    }

    @FXML
    public void annuleSecouriste() {
        removeOverlay();
        rootPane.getChildren().removeAll();
    }
}
