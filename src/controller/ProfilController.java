package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import model.data.persistence.Secouriste;

public class ProfilController {

    @FXML
    Circle myCircle;

    @FXML
    Label nomLabel;

    @FXML
    Label metierLabel;

    @FXML
    Button notifBouton;

    @FXML
    public void initialize() {
        Secouriste secouriste = new Secouriste(1, "Carré", "Lucien", "24 mars 2006", "luciencarre@quelque.chose", "01 23 45 67 89", "23 rue Boule Frite Vannes");

        nomLabel.setText(secouriste.getPrenom() + "  " + secouriste.getNom());
        metierLabel.setText("Rien");

        myCircle.setStroke(Color.BLACK);
        Image image = new Image("/resources/images/pdp.png", false);
        myCircle.setFill(new ImagePattern(image));

    }
}
