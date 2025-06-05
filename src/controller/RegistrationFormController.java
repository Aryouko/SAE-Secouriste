package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static controller.UtilsController.linkToPage;
import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class RegistrationFormController {

    @FXML
    private AnchorPane pageRegistrationForm;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField forenameTextField;
    @FXML
    private TextField birthdateTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private void buttonVerifyClicked() {
        String name = nameTextField.getText();
        String forename = forenameTextField.getText();
        String birthdate = birthdateTextField.getText();
        String address = addressTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        getInstanceAuthentificationManagement().createRescuerFromUser(getInstanceAuthentificationManagement().getCurrentUser().getIdUser() ,name, forename, birthdate, address, phoneNumber);
        linkToPage(pageRegistrationForm, "/fxml/Connexion.fxml");
    }
}
