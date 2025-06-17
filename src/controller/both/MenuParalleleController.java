package controller.both;

import controller.admin.EvenementController;
import controller.admin.FenetreGestionController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.io.IOException;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class MenuParalleleController {

    private FenetreGestionController fenetreGestionController;

    private EvenementController evenementController;

    private boolean isOpenSettings = false;

    @FXML
    private GridPane composentGrid;

    @FXML
    public void initialize() {
        System.out.println("MenuParalleleController initialized");
        setRow(0, "/fxml/both/Profil.fxml");
        setRow(1, "/fxml/both/Calendar.fxml");
        setRow(2, "/fxml/admin/Evenement.fxml");
    }

    @FXML
    public void linkToSettings() {
        if (!isOpenSettings) {
            isOpenSettings = true;
            if (getInstanceAuthentificationManagement().isAdmin()) {
                // Affichage du panneau de paramètres pour l'admin
                fenetreGestionController.loadContent2("/fxml/both/SettingsTemp.fxml");
                setRow(2, "/fxml/both/CalendarDisponibilites.fxml");
                setRow(1, null);
            } else {
                // Affichage du panneau de paramètres pour un secouriste
                fenetreGestionController.loadContent2("/fxml/both/SettingsTemp.fxml");
                setRow(1, null);
                setRow(2, "/fxml/both/CalendarDisponibilites.fxml");

            }
        } else {
            isOpenSettings = false;
            // Retour à la vue principale selon le profil
            if (fenetreGestionController != null) {
                if (getInstanceAuthentificationManagement().isAdmin()) {
                    fenetreGestionController.loadContent2("/fxml/admin/GestionEvenement.fxml");
                } else {
                    fenetreGestionController.loadContent2("/fxml/both/CalendarAssignment.fxml");
                }
            }
            setRow(1, "/fxml/both/Calendar.fxml");
            setRow(2, "/fxml/admin/Evenement.fxml");
        }
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    void setRow(int rowIndex, String fxmlPath) {
        try {
            composentGrid.getChildren().removeIf(node ->
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex
            );

            if(fxmlPath != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node node = loader.load();
                Object controller = loader.getController();
                if (controller instanceof ProfilController) {
                    ((ProfilController) controller).setMenuParalleleController(this);
                } else if (controller instanceof EvenementController) {
                    this.evenementController = (EvenementController) controller;
                }
                composentGrid.add(node, 0, rowIndex);
                GridPane.setHalignment(node, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(node, javafx.geometry.VPos.BOTTOM);
                GridPane.setHgrow(node, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(node, javafx.scene.layout.Priority.ALWAYS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getComposentGrid() {
        return composentGrid;
    }

    public EvenementController getEvenementController() {
        return evenementController;
    }
}
