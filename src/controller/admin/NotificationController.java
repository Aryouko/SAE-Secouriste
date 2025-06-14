package controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.data.persistence.DPS;
import model.data.persistence.Notification;
import model.data.service.AuthentificationManagement;
import model.data.service.NotificationManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NotificationController {

    // NOTIFICATION VIEW
    @FXML
    private ScrollPane scrollPaneNotification ;
    @FXML
    private Button openFormButton ;
    @FXML
    private VBox VBoxlistNotification;

    private final NotificationManagement notificationManagement = new NotificationManagement();
    private final AuthentificationManagement auth = AuthentificationManagement.getInstanceAuthentificationManagement();


    // NOTIFICATION VIEW
    @FXML
    public void initialize() {
        long id = auth.getCurrentUser().getIdUser() ;
        List<Notification> listNotification = this.notificationManagement.getNotificationById(id);

        ArrayList<GridPane> list = listNotificationToListGridPane(listNotification);
        VBoxlistNotification.getChildren().clear();
        VBoxlistNotification.getChildren().addAll(list);
        VBoxlistNotification.setSpacing(10);
        VBoxlistNotification.setStyle("-fx-background-color: #121215;");
    }


    private ArrayList<GridPane> listNotificationToListGridPane(List<Notification> listNotification) {

        ArrayList<GridPane> list = new ArrayList<>();
        for (int i = 0; i < listNotification.size(); i++) {
            Notification notification = listNotification.get(i);
            Label label = new Label(notification.getTitle());
            label.setStyle("-fx-text-fill: white; -fx-font-size: 16px");

            Label date = new Label(notification.getDate());
            date.setStyle("-fx-text-fill: white; -fx-font-size: 12px");

            Circle circle = new Circle();
            circle.setRadius(15);

            GridPane pane = new GridPane();
            GridPane subPane = new GridPane();

            ColumnConstraints col = new ColumnConstraints();
            col.setMaxWidth(500);
            col.setPrefWidth(500);
            pane.getColumnConstraints().addAll(col);

            subPane.add(label, 0, 0);
            subPane.add(date, 0, 1);
            pane.add(subPane,0,0);
            pane.add(circle,1,0);
            pane.setPrefHeight(60);
            pane.setPadding(new Insets(10));
            VBox.setMargin(pane, new Insets(0,10,0,0));
            pane.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 15");
            label.setLayoutX(10);
            label.setLayoutY(10);
            list.add(pane);
        }
        return list;
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
