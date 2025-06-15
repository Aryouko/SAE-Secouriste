package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.data.persistence.Notification;
import model.data.service.AdministrateurManagement;
import model.data.service.DPSManagement;

public class ReadNotificationController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label fromTextlabel;

    @FXML
    private Label toTextlabel;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Label dateTextlabel;

    private Notification notif;

    private AdministrateurManagement admin = new AdministrateurManagement();


    private DPSManagement dps = new DPSManagement();


    @FXML
    private void initialize() {
        titleLabel.setText(notif.getTitle());
        fromTextlabel.setText(admin.getAdministrateurById(notif.getSender()).getNom());
        toTextlabel.setText(dps.getDpsById(notif.getRecipient()).getName());
        dateTextlabel.setText(notif.getDate());
        messageTextArea.setText(notif.getMessage());
    }


    public void setNotification(Notification notif) {
        this.notif = notif;
    }

}
