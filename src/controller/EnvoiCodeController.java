package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.data.service.AuthentificationManagement;

import java.io.IOException;

import static controller.Alerte.showError;
import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;


public class EnvoiCodeController {
    @FXML
    private AnchorPane pageCode;

    @FXML
    private TextField mailTextField;

    @FXML
    private void linkToConnexion() {
        try {
            // Charger la page Inscription.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
            AnchorPane pageInscription = loader.load();

            // Remplacer le contenu actuel par la nouvelle page
            pageCode.getChildren().setAll(pageInscription);

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
    private void ButtonGetCode() {
        if ( getInstanceAuthentificationManagement().ReceiveCode(mailTextField.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code envoyé");
            alert.setHeaderText(null);
            alert.setContentText("Verifier votre boite mail");
            alert.showAndWait();

            try {
                // Charger la page Inscription.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MotDePasseOublie.fxml"));
                AnchorPane pageInscription = loader.load();

                // Remplacer le contenu actuel par la nouvelle page
                pageCode.getChildren().setAll(pageInscription);

                // Optionnel : si tu veux adapter la taille ou ancrer la nouvelle page
                AnchorPane.setTopAnchor(pageInscription, 0.0);
                AnchorPane.setBottomAnchor(pageInscription, 0.0);
                AnchorPane.setLeftAnchor(pageInscription, 0.0);
                AnchorPane.setRightAnchor(pageInscription, 0.0);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement de la page Inscription.fxml");
            }
        } else {
            showError("Mail inexistant");
        }
    }
}
