package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class FenetreGestionController {

    @FXML
    private StackPane includePane;

    public void initialize() {
        // Charge le premier FXML au démarrage
        loadContent("/fxml/GestionEvenement.fxml");
    }

    public void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            includePane.getChildren().setAll(newContent);
            Object controller = loader.getController();
            if (controller instanceof GestionEvenementController) {
                ((GestionEvenementController)controller).setFenetreGestionController(this);
            }
            if (controller instanceof GestionSecouristeController) {
                ((GestionSecouristeController)controller).setFenetreGestionController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
