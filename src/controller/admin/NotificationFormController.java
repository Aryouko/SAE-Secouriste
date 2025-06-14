package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.data.persistence.DPS;

public class NotificationFormController {

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
    private ComboBox<DPS> dpsComboBox;
    @FXML
    private AnchorPane notificationFormAnchorPane;
    @FXML
    private Button closeButtonNotificationForm;

    @FXML
    public void initialize() {

    }

    /**
     * Create a Box Massage to use in the eventController and
     */
    @FXML
    public void sendMessage() {

    }

    @FXML
    private void closeNotificationForm() {
        Stage stage = (Stage) closeButtonNotificationForm.getScene().getWindow();
        stage.close();
    }
}
