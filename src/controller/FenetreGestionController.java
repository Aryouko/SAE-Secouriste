package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class FenetreGestionController {

    @FXML
    private StackPane includePane1;

    @FXML
    private StackPane includePane2;

    @FXML
    private AnchorPane FenetreGestion;

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
                ((GestionEvenementController)controller).setFenetreGestionController(this);
            }
            if (controller instanceof GestionSecouristeController) {
                ((GestionSecouristeController)controller).setFenetreGestionController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getFenetreGestion() {
        return FenetreGestion;
    }
}
