package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.data.service.AuthentificationManagement;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passwordPasswordField;
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
        AuthentificationManagement authentificationManagement = new AuthentificationManagement();
        if ( authentificationManagement.authenticate(mailTextField.toString(), passwordPasswordField.toString())) {
            System.out.println("Tentative de connexion reussi");
            try {
                // Charger la page Inscription.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FenetreEvenement.fxml"));
                AnchorPane pageEvenement = loader.load();

                // Remplacer le contenu actuel par la nouvelle page
                pageConnexion.getChildren().setAll(pageEvenement);

                AnchorPane.setTopAnchor(pageEvenement, 0.0);
                AnchorPane.setBottomAnchor(pageEvenement, 0.0);
                AnchorPane.setLeftAnchor(pageEvenement, 0.0);
                AnchorPane.setRightAnchor(pageEvenement, 0.0);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement de la page Inscription.fxml");
            }
        }
        System.out.println("Connexion button clicked");
        System.out.println("Mail: " + mailTextField.getText());
        System.out.println("Password: " + passwordPasswordField.getText());


    }


}
