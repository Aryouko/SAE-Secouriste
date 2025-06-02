package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private Label connexionLoginLabel;
    @FXML
    private PasswordField connexionPasswordLabel;

    @FXML
    public void ButtonConnexionClicked() {
        // Logique de connexion ici
        System.out.println("Connexion button clicked");
    }

    @FXML
    public void initialize() {
        // Initialisation de la vue de connexion
        connexionLoginLabel.setAlignment(Pos.CENTER);
        connexionPasswordLabel.setAlignment(Pos.CENTER);
        System.out.println("ConnexionController initialized");
    }

    @FXML
    public void ButtonMotDePasseOublieClicked(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inscription.fxml"));
            Parent root = loader.load();

            // Récupère la scène actuelle à partir de l’événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
