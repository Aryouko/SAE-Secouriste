package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import model.dao.SecouristeDAO;
import model.data.persistence.Secouriste;
import model.data.service.AuthentificationManagement;

public class ProfilController {

    @FXML
    Circle myCircle;

    @FXML
    Label nomLabel;

    @FXML
    Label adminSecourLabel;

    @FXML
    Button notifBouton;

    @FXML
    public void initialize() {
        AuthentificationManagement auth = new AuthentificationManagement();
        SecouristeDAO secouristeDAO = new SecouristeDAO();
        if (auth.getCurrentUser().getRole() = "rescuer") {
            Secouriste secouriste = secouristeDAO.findSecouriste(auth.getCurrentUser().getIdUser());
            nomLabel.setText(secouriste.getPrenom() + "  " + secouriste.getNom());
            adminSecourLabel.setText("Secouriste");
        } else {
            Administrateur administrateur = Administrateur.findAdministrateur(auth.getCurrentUser().getIdUser());
            nomLabel.setText(administrateur.getPrenom() + "  " + administrateur.getNom());
            adminSecourLabel.setText("Administrateur");
        }


        myCircle.setStroke(Color.BLACK);
        Image image = new Image("/images/pdp.png", false);
        myCircle.setFill(new ImagePattern(image));

    }
}
