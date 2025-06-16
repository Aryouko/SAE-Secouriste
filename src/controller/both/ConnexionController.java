package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.data.service.AuthentificationManagement;
import static controller.UtilsController.linkToPage;
import static controller.UtilsController.showError;
import static model.data.service.AuthentificationManagement.LoginResult.*;
import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

/**
 * ConnexionController is responsible for handling the login page of the application.
 * It provides functionality to log in, navigate to the registration page, and recover forgotten passwords.
 */
public class ConnexionController {

    /**
     * The AnchorPane that serves as the login page.
     */
    @FXML
    private TextField mailTextField;
    /**
     * The PasswordField for entering the password.
     */
    @FXML
    private PasswordField passwordPasswordField;
    /**
     * The AnchorPane that contains the login page layout.
     */
    @FXML
    private AnchorPane pageConnexion;

    /**
     * Initializes the ConnexionController by setting up the login page.
     * This method is called automatically when the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        System.out.println("ConnexionController initialized");
    }

    /**
     * Links to the registration page when the "Register" button is clicked.
     */
    @FXML
    public void linkToRegister() {
        linkToPage(pageConnexion, "/fxml/both/Inscription.fxml");
    }

    /**
     * Links to the password recovery page when the "Forgot Password" link is clicked.
     */
    @FXML
    public void linkToReceiveCode() {
        linkToPage(pageConnexion, "/fxml/both/EnvoiCode.fxml");
    }

    @FXML
    public void quickLogin() {

    }

    /**
     * Handles the login button click event.
     * It attempts to log in with the provided email and password.
     * If successful, it navigates to the event page; otherwise, it shows an error message.
     */
    @FXML
    public void ButtonConnexionClicked() {
        System.out.println("Connexion button clicked");
        try {
            AuthentificationManagement.LoginResult result = getInstanceAuthentificationManagement().login(mailTextField.getText(), passwordPasswordField.getText());
            if (result == SUCCESS) {
                linkToPage(pageConnexion, "/fxml/admin/FenetreGestion.fxml");
            } else if (result ==INVALID_RESCUER) {
                linkToPage(pageConnexion, "/fxml/both/RegistrationForm.fxml");
            } else {
                if (result == INVALID_LOGIN) {
                    mailTextField.clear();
                    mailTextField.setPromptText("Adresse mail inconnue");
                    mailTextField.requestFocus();

                    passwordPasswordField.clear();
                    passwordPasswordField.requestFocus();
                }
                if (result == INVALID_PASSWORD) {
                    passwordPasswordField.clear();
                    passwordPasswordField.setPromptText("Mot de passe incorrect");
                    passwordPasswordField.requestFocus();
                }
            }
        } catch (Exception e) {
            showError("Une erreur est survenue lors de la connexion. Veuillez réessayer plus tard.");
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }

    @FXML
    public void enterConnectionClicked(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            ButtonConnexionClicked();
        }
    }
}
