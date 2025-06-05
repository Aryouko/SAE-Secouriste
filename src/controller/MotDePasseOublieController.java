package controller;

    import javafx.scene.control.TextField;
    import javafx.scene.layout.AnchorPane;
    import javafx.fxml.FXML;

    import static controller.UtilsController.linkToPage;
    import static controller.UtilsController.showError;
    import static controller.UtilsController.showInfo;
    import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

    public class MotDePasseOublieController {
        @FXML
        private AnchorPane pageForgotPassword;
        @FXML
        private TextField codeTextField;
        @FXML
        private TextField passwordTextField;

        @FXML
        private void linkToConnexion() {
            linkToPage(pageForgotPassword, "/fxml/Connexion.fxml");
        }

        @FXML
        private void validateChange() {
            try {
                long code = Long.parseLong(codeTextField.getText());
                if (getInstanceAuthentificationManagement().changePassword(code, passwordTextField.getText())) {
                    showInfo("Le mot de passe a été changé avec succès !");
                    linkToConnexion();
                } else {
                    showError("Le mot de passe n'a pas été changé, le code n'est pas bon !");
                }
            } catch (NumberFormatException e) {
                showError("Le code doit être un nombre.");
            }
        }
    }