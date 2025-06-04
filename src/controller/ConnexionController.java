package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passwordPassswordField;
    @FXML
    private AnchorPane pageConnexion;

    @FXML
    public void initialize() {


        // connexionLoginLabel.setAlignment(Pos.CENTER);
        // connexionPasswordLabel.setAlignment(Pos.CENTER);
        System.out.println("ConnexionController initialized");
    }

    @FXML
    public void linkToRegister() {
        try {
            // Charger la page Inscription.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inscription.fxml"));
            AnchorPane pageInscription = loader.load();

            // Remplacer le contenu actuel par la nouvelle page
            pageConnexion.getChildren().setAll(pageInscription);

            // Optionnel : si tu veux adapter la taille ou ancrer la nouvelle page
            AnchorPane.setTopAnchor(pageInscription, 0.0);
            AnchorPane.setBottomAnchor(pageInscription, 0.0);
            AnchorPane.setLeftAnchor(pageInscription, 0.0);
            AnchorPane.setRightAnchor(pageInscription, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Inscription.fxml");
        }
    }

    @FXML
    public void linkToReceiveCode() {
        try {
            // Charger la page Inscription.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnvoiCode.fxml"));
            AnchorPane pageMotDePasseOublie = loader.load();

            // Remplacer le contenu actuel par la nouvelle page
            pageConnexion.getChildren().setAll(pageMotDePasseOublie);

            // Optionnel : si tu veux adapter la taille ou ancrer la nouvelle page
            AnchorPane.setTopAnchor(pageMotDePasseOublie, 0.0);
            AnchorPane.setBottomAnchor(pageMotDePasseOublie, 0.0);
            AnchorPane.setLeftAnchor(pageMotDePasseOublie, 0.0);
            AnchorPane.setRightAnchor(pageMotDePasseOublie, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page Inscription.fxml");
        }
    }


    @FXML
    public void ButtonConnexionClicked() {
        // Logique de connexion ici

        System.out.println("Connexion button clicked");

        System.out.println("Mail: " + mailTextField.getText());
        System.out.println("Password: " + passwordPassswordField.getText());



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
