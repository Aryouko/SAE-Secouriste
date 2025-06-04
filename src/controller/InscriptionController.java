package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.data.service.AuthentificationManagement;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField labelMail;

    @FXML
    private PasswordField labelPassword;

    @FXML
    private PasswordField labelPasswordVerification;

    @FXML
    private AnchorPane pageRegister;




    @FXML
    public void ButtonRegisterClicked() {
        // Logique d'inscription ici
        System.out.println("Inscription button clicked");
        AuthentificationManagement authentificationManagement = new AuthentificationManagement();
        System.out.println("Email: " + labelMail.getText());
        authentificationManagement.registration(labelMail.getText(), labelPassword.getText(), labelPasswordVerification.getText());

        // Afficher le pop-up d'inscription réussie
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Inscription réussie !");
        alert.showAndWait();

        // Rediriger vers la page de connexion
        linkToConnexion();
    }






    @FXML
    private void linkToConnexion() {
        try {
            // Charger la page Inscription.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
            AnchorPane pageInscription = loader.load();

            // Remplacer le contenu actuel par la nouvelle page
            pageRegister.getChildren().setAll(pageInscription);

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
}
