package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class RegistrationFormController {

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
        // Logic to verify the registration form inputs
        String name = nameTextField.getText();
        String forename = forenameTextField.getText();
        String birthdate = birthdateTextField.getText();
        String address = addressTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        // Here you would typically validate the inputs and proceed with the registration process
        System.out.println("Name: " + name);
        System.out.println("Forename: " + forename);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);

        // Add your validation logic here
        getInstanceAuthentificationManagement().createRescuerFromUser(getInstanceAuthentificationManagement().getCurrentUser().getIdUser() ,name, forename, birthdate, address, phoneNumber);
    }
}
