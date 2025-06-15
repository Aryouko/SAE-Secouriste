package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static controller.UtilsController.linkToPage;
import static controller.UtilsController.showError;
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

        if (getInstanceAuthentificationManagement().createRescuer(getInstanceAuthentificationManagement().getCurrentUser().getIdUser() ,name, forename, birthdate, address, phoneNumber)) {;
            System.out.println("Rescuer created successfully.");
            linkToPage(pageRegistrationForm, "/fxml/both/Connexion.fxml");
        } else {
            showError("Impossible de créer le secouriste. Veuillez vérifier vos informations.");
        }
    }
}
