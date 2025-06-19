package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.data.persistence.*;
import model.data.service.*;

import static controller.admin.FenetreGestionController.removeOverlay;
import static controller.admin.FenetreGestionController.showOverlay;

/**
 * Controller for the rescuer deletion confirmation window.
 * This controller handles the deletion of a rescuer and its associated data.
 * @author L. Carré, G. Potay, C. Brocart, T.Brami-Coatual
 * @version 1.0
 */
public class FenetreSupprSecouristeController {

    /**
     * The root pane of the window, used to manage the overlay.
     */
    @FXML
    private AnchorPane rootPane;

    /**
     * The stage of the window, used to close it after deletion.
     */
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    /**
     * The management service for affectations, used to handle related data.
     */
    private final AffectationManagement affectationManagement = new AffectationManagement();

    /**
     * The management service for possessions, used to handle related data.
     */
    private final PossessionManagement possessionManagement = new PossessionManagement();

    /**
     * The management service for disponibilites, used to handle related data.
     */
    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();

    /**
     * The rescuer to be deleted, initialized when the window is opened.
     */
    private Secouriste secouriste;

    /**
     * The controller for managing rescuers, used to update the rescuer list after deletion.
     */
    private GestionSecouristeController gestionSecouristeController;

    /**
     * Initializes the controller with the necessary data.
     *
     * @param secouriste The rescuer to be deleted.
     * @param gestionSecouristeController The controller for managing rescuers.
     */
    public void initializeSupprSecouriste(Secouriste secouriste, GestionSecouristeController gestionSecouristeController) {
        this.secouriste = secouriste;
        this.gestionSecouristeController = gestionSecouristeController;
    }

    /**
     * Displays an overlay to confirm the deletion of the rescuer.
     */
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

    /**
     * Cancel the deletion of the rescuer and closes the confirmation window.
     */
    @FXML
    public void annuleSecouriste() {
        removeOverlay();
        rootPane.getChildren().removeAll();
    }
}
