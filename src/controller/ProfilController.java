package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import model.dao.AdministrateurDAO;
import model.dao.SecouristeDAO;
import model.data.persistence.Administrateur;
import model.data.persistence.Secouriste;
import model.data.service.AuthentificationManagement;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

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
        AuthentificationManagement auth = getInstanceAuthentificationManagement();
        if (auth.getCurrentUser().getRole().equals("rescuer")) {
            SecouristeDAO secouristeDAO = new SecouristeDAO();
            Secouriste secouriste = secouristeDAO.findById(auth.getCurrentUser().getIdUser());
            nomLabel.setText(secouriste.getPrenom() + "  " + secouriste.getNom());
            adminSecourLabel.setText("Secouriste");
        } else {
            AdministrateurDAO administrateurDAO = new AdministrateurDAO();
            Administrateur administrateur = administrateurDAO.findById(auth.getCurrentUser().getIdUser());
            nomLabel.setText(administrateur.getPrenom() + "  " + administrateur.getNom());
            adminSecourLabel.setText("Administrateur");
        }


        myCircle.setStroke(Color.BLACK);
        Image image = new Image("/images/pdp.png", false);
        myCircle.setFill(new ImagePattern(image));

    }

}
