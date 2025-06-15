package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FenetreGestionController {

    @FXML
    private StackPane includePane1;

    @FXML
    private StackPane includePane2;

    @FXML
    private AnchorPane fenetreGestion;

    private GestionEvenementController gestionEvenementController;

    private GestionSecouristeController gestionSecouristeController;

    private MenuParalleleController menuParalleleController;

    public void initialize() {
        loadContent1("/fxml/MenuParallele.fxml");
        loadContent2("/fxml/GestionEvenement.fxml");
    }

    public void loadContent1(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            this.includePane1.getChildren().setAll(newContent);
            Object controller = loader.getController();

            this.menuParalleleController = (MenuParalleleController) controller;
            ((MenuParalleleController)controller).setFenetreGestionController(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadContent2(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            this.includePane2.getChildren().setAll(newContent);
            Object controller = loader.getController();
            if (controller instanceof GestionEvenementController) {
                this.gestionEvenementController = (GestionEvenementController) controller;
                ((GestionEvenementController)controller).setFenetreGestionController(this);
                ((GestionEvenementController) controller).setEvenementController(this.menuParalleleController.getEvenementController());
            }
            if (controller instanceof GestionSecouristeController) {
                this.gestionSecouristeController = (GestionSecouristeController) controller;
                ((GestionSecouristeController)controller).setFenetreGestionController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getFenetreGestion() {
        return this.fenetreGestion;
    }

    public void fenetreCreationDPS() {
        try {
            // Charger le FXML de la nouvelle fenêtre (à créer)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FenetreAjoutDPS.fxml"));
            Parent overlayContent = loader.load();

            FenetreAjoutDPSController controller = loader.getController();
            controller.initializeGestionEvenementController(this.gestionEvenementController);
            controller.initializeEvenementController(this.menuParalleleController.getEvenementController());

            StackPane overlayPane = new StackPane();
            overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // fond semi-transparent sombre

            overlayPane.getChildren().add(overlayContent);
            StackPane.setAlignment(overlayContent, Pos.CENTER);

            AnchorPane.setTopAnchor(overlayPane, 0.0);
            AnchorPane.setBottomAnchor(overlayPane, 0.0);
            AnchorPane.setLeftAnchor(overlayPane, 0.0);
            AnchorPane.setRightAnchor(overlayPane, 0.0);

            this.fenetreGestion.getChildren().add(overlayPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
