package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import model.data.persistence.Secouriste;
import model.data.service.AuthentificationManagement;
import model.data.service.SecouristeManagement;
import javafx.application.Platform;

public class SettingsController {

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private Secouriste sec; // garder la référence pour annuler

    @FXML
    private TextField pseudoField;
    @FXML
    private TextField bioField;
    @FXML
    private Text prenomNomProfile;
    @FXML
    private Text prenomPrenomParam;

    @FXML
    public void initialize() {
        sec = AuthentificationManagement.getInstanceAuthentificationManagement().getSecouriste();
        if (sec != null) {
            prenomNomProfile.setText(sec.getPrenom() + " " + sec.getNom());
            prenomPrenomParam.setText(sec.getPrenom() + " " + sec.getNom());
            pseudoField.setText(sec.getPseudo());
            bioField.setText(sec.getBio());
        }
    }

    @FXML
    private void saveClicked() {
        if (sec != null) {
            sec.setPseudo(pseudoField.getText());
            sec.setBio(bioField.getText());

            boolean success = secouristeManagement.updateSecouriste(sec);
            if (success) {
                showPopup("Sauvegardé !");
            } else {
                showPopup("Erreur lors de la sauvegarde.");
            }
        }
    }

    @FXML
    private void cancelClicked() {
        if (sec != null) {
            pseudoField.setText(sec.getPseudo());
            bioField.setText(sec.getBio());
            showPopup("Modifications annulées.");
        }
    }

    private void showPopup(String message) {
        // Popup simple avec JavaFX Alert ou juste afficher un message temporaire
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        // Fermer la popup au bout de 1 seconde
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(alert::close);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
