package controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import static controller.UtilsController.linkToPage;
import static controller.UtilsController.showInfo;
import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;


public class EnvoiCodeController {
    @FXML
    private AnchorPane pageCode;
    @FXML
    private TextField mailTextField;
    @FXML
    private void linkToConnexion() {
        linkToPage(pageCode, "/fxml/Connexion.fxml");
    }

    @FXML
    private void ButtonGetCode() {
        if (getInstanceAuthentificationManagement().ReceiveCode(mailTextField.getText())) {
            showInfo("Code envoyé avec succès !");
            linkToPage(pageCode, "/fxml/MotDePasseOublie.fxml");
        } else {
            UtilsController.showError("Mail inexistant");
        }
    }
}

