package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import static controller.UtilsController.linkToPage;
import static controller.UtilsController.showError;
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
        linkToPage(pageConnexion, "/fxml/Inscription.fxml");
    }

    @FXML
    public void linkToReceiveCode() {
        linkToPage(pageConnexion, "/fxml/EnvoiCode.fxml");
    }

    @FXML
    public void ButtonConnexionClicked() {
        System.out.println("Connexion button clicked");
        if (getInstanceAuthentificationManagement().login(mailTextField.getText(), passwordPasswordField.getText())) {
            linkToPage(pageConnexion, "/fxml/FenetreEvenement.fxml");
        } else {
            showError("Identifiant ou mot de passe incorrect. Veuillez réessayer.");
        }
    }
}
