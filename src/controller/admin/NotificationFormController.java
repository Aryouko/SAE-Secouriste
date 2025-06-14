package controller.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.data.persistence.DPS;
import model.data.service.AuthentificationManagement;
import model.data.service.DPSManagement;
import model.data.service.NotificationManagement;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

public class NotificationFormController {

    private LocalDateTime now = LocalDateTime.now();
    private final AuthentificationManagement auth = new AuthentificationManagement();
    private final DPSManagement dpsMana = new DPSManagement();
    private final NotificationManagement notificationManagement = new NotificationManagement();

    // FORM
    @FXML
    private TextField subjectTextField;
    @FXML
    private Label dateTextlabel;
    @FXML
    private Label fromTextlabel;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private ComboBox<String> dpsComboBox;
    @FXML
    private AnchorPane notificationFormAnchorPane;
    @FXML
    private Button closeButtonNotificationForm;

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH'h'mm d MMMM", Locale.FRENCH);
        String formatted = now.format(formatter);
        dateTextlabel.setText(formatted);

        String UserName = auth.getCurrentUserName();
        fromTextlabel.setText(UserName);

        dpsComboBox.setItems(FXCollections.observableArrayList(dpsMana.getDpsName()));
    }

    /**
     * Create a Box Massage to use in the eventController and
     */
    @FXML
    public void sendMessage() {


        String title = subjectTextField.getText();
        String message = messageTextArea.getText();
        String date = dateTextlabel.getText();
        String DPSName = dpsComboBox.getValue();

        notificationManagement.createNotification(title, message, date, DPSName);



    }



    @FXML
    private void closeNotificationForm() {
        Stage stage = (Stage) closeButtonNotificationForm.getScene().getWindow();
        stage.close();
    }
}
