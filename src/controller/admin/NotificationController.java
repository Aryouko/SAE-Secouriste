package controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;

import java.io.IOException;

public class NotificationController {

    // NOTIFICATION VIEW
    @FXML
    private ScrollPane scrollPaneNotification ;
    @FXML
    private Button openFormButton ;


    // NOTIFICATION VIEW
    @FXML
    public void initialize() {
    }

    /**
     * openForm
     */
    public void openForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/NotificationForm.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }
}
