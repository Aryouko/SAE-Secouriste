package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class ConnexionController {

    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private AnchorPane pageConnexion;

    @FXML
    public void initialize() {
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
            System.err.println("Erreur lors du chargement de la page Inscription.fxml : " + e.getMessage());
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
            System.err.println("Erreur lors du chargement de la page ReceiveCode.fxml : " + e.getMessage());
        }
    }


    @FXML
    public void ButtonConnexionClicked() {
        // Logique de connexion ici
        System.out.println("Connexion button clicked");
        if (getInstanceAuthentificationManagement().login(mailTextField.getText(), passwordPasswordField.getText())) {
            System.out.println("login et mdp bons");
            try {
                // Load Evenement
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FenetreEvenement.fxml"));
                AnchorPane pageEvenement = loader.load();

                // Remplacer le contenu actuel par la nouvelle page
                pageConnexion.getChildren().setAll(pageEvenement);

                AnchorPane.setTopAnchor(pageEvenement, 0.0);
                AnchorPane.setBottomAnchor(pageEvenement, 0.0);
                AnchorPane.setLeftAnchor(pageEvenement, 0.0);
                AnchorPane.setRightAnchor(pageEvenement, 0.0);

            } catch (IOException e) {
                System.err.println("Erreur lors du clique bouton connexion : " + e.getMessage());
            }
        } else {
            // Afficher le pop-up d'inscription réussie
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mauvais identifiant");
            alert.setHeaderText(null);
            alert.setContentText("Mauvais identifiant ! \n Vérifier les identifier ");
            alert.showAndWait();
        }
    }
}
