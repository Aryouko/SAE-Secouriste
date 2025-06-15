package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.service.AffectationManagement;
import model.data.service.BesoinManagement;
import model.data.service.DPSManagement;

public class FenetreSupprDPSController {

    @FXML
    private AnchorPane rootPane;

    private final DPSManagement dpsManagement = new DPSManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private DPS dps;

    private GestionEvenementController gestionEvenementController;

    private EvenementController evenementController;

    private BesoinManagement besoinManagement = new BesoinManagement();

    public void initializeDPS(DPS dps, GestionEvenementController gestionEvenementController, EvenementController evenementController) {
        this.dps = dps;
        this.gestionEvenementController = gestionEvenementController;
        this.evenementController = evenementController;
    }

    @FXML
    public void supprDps() {
        this.besoinManagement.removeByDps(this.dps);
        for (Affectation affectation : this.affectationManagement.getAffectationsByDps(this.dps)) {
            this.affectationManagement.removeByDps(affectation);
        }
        this.dpsManagement.removeDps(this.dps);
        this.gestionEvenementController.retirerDpsList(dps);
        this.gestionEvenementController.filtreUpdate();
        this.evenementController.initialize();
        annuleDPS();
    }

    @FXML
    public void annuleDPS() {
        // Le parent de rootPane est overlayPane (StackPane)
        Node overlayPane = rootPane.getParent();

        // Le parent de overlayPane est fenetreGestion (AnchorPane)
        if (overlayPane != null && overlayPane.getParent() instanceof AnchorPane) {
            AnchorPane fenetreGestion = (AnchorPane) overlayPane.getParent();
            fenetreGestion.getChildren().remove(overlayPane);
        }
    }
}
