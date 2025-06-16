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
        boolean isValid = true;

        // Vérification du nom (non vide et uniquement des lettres)
        String name = nameTextField.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-ZÀ-ÿ\\s-]+")) {
            nameTextField.setStyle("-fx-prompt-text-fill: #FF004D;");
            nameTextField.setText("");
            nameTextField.setPromptText("Nom invalide");
            isValid = false;
        } else {
            nameTextField.setStyle("");
        }

        // Vérification du prénom (non vide et uniquement des lettres)
        String forename = forenameTextField.getText().trim();
        if (forename.isEmpty() || !forename.matches("[a-zA-ZÀ-ÿ\\s-]+")) {
            forenameTextField.setStyle("-fx-prompt-text-fill: #FF004D;");
            forenameTextField.setText("");
            forenameTextField.setPromptText("Prénom invalide");
            isValid = false;
        } else {
            forenameTextField.setStyle("");
        }

        // Vérification de la date de naissance (format JJ/MM/AA)
        String birthdate = birthdateTextField.getText().trim();
        if (!birthdate.matches("\\d{2}/\\d{2}/\\d{2}")) {
            birthdateTextField.setStyle("-fx-prompt-text-fill: #FF004D;");
            birthdateTextField.setText("");
            birthdateTextField.setPromptText("Format JJ/MM/AA requis");
            isValid = false;
        } else {
            birthdateTextField.setStyle("");
        }

        // Vérification de l'adresse (non vide)
        String address = addressTextField.getText().trim();
        if (address.isEmpty()) {
            addressTextField.setStyle("-fx-prompt-text-fill: #FF004D;");
            addressTextField.setText("");
            addressTextField.setPromptText("Adresse requise");
            isValid = false;
        } else {
            addressTextField.setStyle("");
        }

        // Vérification du numéro de téléphone (10 chiffres)
        String phoneNumber = phoneNumberTextField.getText().trim();
        if (!phoneNumber.matches("\\d{10}")) {
            phoneNumberTextField.setStyle("-fx-prompt-text-fill: #FF004D;");
            phoneNumberTextField.setText("");
            phoneNumberTextField.setPromptText("10 chiffres requis");
            isValid = false;
        } else {
            phoneNumberTextField.setStyle("");
        }

        // Si tous les champs sont valides, on crée le secouriste
        if (isValid) {
            if (getInstanceAuthentificationManagement().createRescuer(getInstanceAuthentificationManagement().getCurrentUser().getIdUser(), name, forename, birthdate, phoneNumber, address,  null)) {
                System.out.println("Rescuer created successfully.");
                linkToPage(pageRegistrationForm, "/fxml/both/Connexion.fxml");
            } else {
                showError("Impossible de créer le secouriste. Veuillez vérifier vos informations.");
            }
        }
    }
}