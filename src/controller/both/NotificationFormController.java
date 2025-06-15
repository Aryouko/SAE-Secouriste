package controller.both;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.data.service.AuthentificationManagement;
import model.data.service.DPSManagement;
import model.data.service.NotificationManagement;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class NotificationFormController {

    private LocalDateTime now = LocalDateTime.now();
    private final AuthentificationManagement auth = AuthentificationManagement.getInstanceAuthentificationManagement();;
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
        notificationFormAnchorPane.getStylesheets().add(getClass().getResource("/css/errors.css").toExternalForm());

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
    public void sendMessage() {
        boolean valid = true;

        // Sujet
        if (subjectTextField.getText() == null || subjectTextField.getText().trim().isEmpty()) {
            if (!subjectTextField.getStyleClass().contains("field-error")) {
                subjectTextField.getStyleClass().add("field-error");
            }
            valid = false;
        } else {
            subjectTextField.getStyleClass().removeAll("field-error");
        }

        // Message
        if (messageTextArea.getText() == null || messageTextArea.getText().trim().isEmpty()) {
            if (!messageTextArea.getStyleClass().contains("field-error")) {
                messageTextArea.getStyleClass().add("field-error");
            }
            valid = false;
        } else {
            messageTextArea.getStyleClass().removeAll("field-error");
        }

        // ComboBox
        if (dpsComboBox.getValue() == null || dpsComboBox.getValue().trim().isEmpty()) {
            if (!dpsComboBox.getStyleClass().contains("comboBox-error")) {
                dpsComboBox.getStyleClass().add("comboBox-error");
            }
            valid = false;
        } else {
            dpsComboBox.getStyleClass().removeAll("comboBox-error");
        }

        if (!valid) return;

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
