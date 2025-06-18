package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import model.data.persistence.Administrateur;
import model.data.persistence.Secouriste;
import model.data.service.AdministrateurManagement;
import model.data.service.AuthentificationManagement;
import model.data.service.SecouristeManagement;

import java.io.ByteArrayInputStream;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class ProfilController implements MenuParalleleInjectable {

    @FXML
    Circle myCircle;

    @FXML
    Label nomLabel;

    @FXML
    Label adminSecourLabel;

    @FXML
    Button notifBouton;

    private AdministrateurManagement administrateurManagement = new AdministrateurManagement();

    private SecouristeManagement secouristeManagement = new SecouristeManagement();

    private MenuParalleleController menuParalleleController;

    private boolean isNotif = false ;

    @FXML
    public void initialize() {
        AuthentificationManagement auth = getInstanceAuthentificationManagement();
        if (auth.getCurrentUser().getRole().equals("rescuer")) {
            Secouriste secouriste = secouristeManagement.getSecouristeById(auth.getCurrentUser().getIdUser());
            nomLabel.setText(secouriste.getPrenom() + " " + secouriste.getNom());
            adminSecourLabel.setText("Secouriste");
        } else {
            Administrateur administrateur = administrateurManagement.getAdministrateurById(auth.getCurrentUser().getIdUser());
            nomLabel.setText(administrateur.getPrenom() + " " + administrateur.getNom());
            adminSecourLabel.setText("Administrateur");
        }

        myCircle.setStroke(Color.BLACK);
        Secouriste secouriste;
        Administrateur administrateur;
        Image image = null;
        if (getInstanceAuthentificationManagement().isAdmin()) {
            administrateur = administrateurManagement.getAdministrateurById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            if (administrateur.getPhoto() != null) {
                image = new Image(new ByteArrayInputStream(administrateur.getPhoto()));
                if (!image.isError()) {
                    myCircle.setFill(new ImagePattern(image));
                }
            }
        } else {
            secouriste = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            if (secouriste.getPhoto() != null) {
                image = new Image(new ByteArrayInputStream(secouriste.getPhoto()));
                if (!image.isError()) {
                    myCircle.setFill(new ImagePattern(image));
                }
            }
        }

        if (image != null) {
            myCircle.setFill(new ImagePattern(image));
        }
    }


    @FXML
    private void switchNotifCalendar() {
        if (menuParalleleController != null) {
            if (!isNotif) {
                menuParalleleController.setRow(1, "/fxml/notif/DisplayNotification.fxml");
                isNotif = true ;
            } else {
                menuParalleleController.setRow(1, "/fxml/common/DisplayCalendar.fxml");
                isNotif = false ;
            }
        }
    }

    public void setMenuParalleleController(MenuParalleleController controller) {
        this.menuParalleleController = controller;
    }
}
