package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import static controller.UtilsController.linkToPage;
import static controller.UtilsController.showError;
import static controller.UtilsController.showInfo;
import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class InscriptionController {

    @FXML
    private TextField labelMail;

    @FXML
    private PasswordField labelPassword;

    @FXML
    private PasswordField newPasswordConfirmation;

    @FXML
    private AnchorPane pageRegister;

    @FXML
    public void ButtonRegisterClicked() {
        try {
            System.out.println("Inscription button clicked");
            System.out.println("Email: " + labelMail.getText());
            if (getInstanceAuthentificationManagement().register(labelMail.getText(), labelPassword.getText(), newPasswordConfirmation.getText())) {
                showInfo("L'inscription a réussi. Vous pouvez maintenant vous connecter.");
                linkToConnexion();
            } else {
                showError("L'inscription a échoué. Veuillez vérifier vos informations.");
            }
        } catch (SQLException e) {
            showError("Une erreur est survenue lors de l'inscription. Veuillez réessayer plus tard.");
        }
    }

    @FXML
    private void linkToConnexion() {
        linkToPage(pageRegister, "/fxml/Connexion.fxml");
    }
}