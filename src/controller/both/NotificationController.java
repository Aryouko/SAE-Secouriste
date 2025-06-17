package controller.both;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.data.persistence.Notification;
import model.data.service.AuthentificationManagement;
import model.data.service.NotificationManagement;

import java.io.IOException;
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

        // Delete the possibilities to send message if you are a rescuer
        openFormButton.setVisible(auth.isAdmin());
        openFormButton.setDisable(!auth.isAdmin());

        List<Notification> listNotification = this.notificationManagement.getNotificationByIdSender(id);
        System.out.println("Nombre de notifications récupérées : " + listNotification.size());

        ArrayList<GridPane> list = listNotificationToListGridPane(listNotification);
        VBoxlistNotification.getChildren().clear();
        VBoxlistNotification.getChildren().addAll(list);
        VBoxlistNotification.setSpacing(10);
        VBoxlistNotification.setStyle("-fx-background-color: #121215;");
    }


    private ArrayList<GridPane> listNotificationToListGridPane(List<Notification> listNotification) {

        ArrayList<GridPane> list = new ArrayList<>();
        model.data.service.DPSManagement dpsManagement = new model.data.service.DPSManagement();
        for (int i = 0; i < listNotification.size(); i++) {
            Notification notification = listNotification.get(i);

            Label label = new Label(notification.getTitle());
            label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

            // Ajout du nom du DPS concerné

            String dpsName = dpsManagement.getDpsById(notification.getIdDPS()).getName();

            Label dpsLabel = new Label(dpsName);

            Label date = new Label(notification.getDate());

            Label infosLabel = new Label(dpsName + " à " + notification.getDate());
            infosLabel.setStyle("-fx-text-fill: #EDF2F66F; " +
                    "-fx-font-family: 'Poppins'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: 300; " +
                    "-fx-letter-spacing: -0.56px;");

            Circle circle = new Circle();
            circle.setRadius(20);
            circle.setFill(javafx.scene.paint.Color.web("#4A4AE4"));



            GridPane pane = new GridPane();
            GridPane subPane = new GridPane();

            subPane.setPadding(new Insets(0, 0, 0, 15));

            ColumnConstraints col = new ColumnConstraints();
            col.setMaxWidth(500);
            col.setPrefWidth(500);
            pane.getColumnConstraints().addAll(col);

            subPane.add(label, 0, 0);
            subPane.add(infosLabel, 0, 1);
            pane.add(subPane,0,0);
            pane.add(circle,1,0);

            // Ajout de la pastille pour nouvelle notif non lue
            Circle badge = null;
            if (!notification.getIsViewed()) {
                badge = new Circle();
                badge.setRadius(6);
                badge.setFill(javafx.scene.paint.Color.web("#FF004D"));
                badge.setTranslateX(290); // décalage à droite
                badge.setTranslateY(-15); // décalage vers le haut
                // Ajout dans le même parent que le cercle
                pane.getChildren().add(badge);
                badge.toFront(); // S'assurer que la pastille est au-dessus
            }



            pane.setPrefHeight(60);
            pane.setMaxHeight(60);
            pane.setPadding(new Insets(10));
            VBox.setMargin(pane, new Insets(0,10,0,0));

            circle.setFill(javafx.scene.paint.Color.web("#4A4AE4")); // Color:
            if (notification.getIsViewed()) {
                pane.setStyle("-fx-background-color: rgba(249, 252, 255, 0.07); -fx-background-radius: 50");
            } else {
                pane.setStyle("-fx-background-color: #46484C; -fx-background-radius: 50");
            }

            label.setLayoutX(10);
            label.setLayoutY(10);



            pane.setOnMouseClicked(event -> {
                try {
                    pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 50");
                    label.setStyle("-fx-text-fill: #000000; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

                    infosLabel.setStyle("-fx-text-fill: rgb(0,0,0); " +
                            "-fx-font-family: 'Poppins'; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 300; " +
                            "-fx-letter-spacing: -0.56px;");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notif/ReadNotification.fxml"));
                    Parent root = loader.load();
                    ReadNotificationController controller = loader.getController();
                    controller.setNotification(notification);

                    Stage popupStage = new Stage();
                    popupStage.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(root);
                    scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                    popupStage.setScene(scene);
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pane.setStyle("-fx-background-color: #2A2B2D; -fx-background-radius: 50");
                label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

                infosLabel.setStyle("-fx-text-fill: #EDF2F66F ; " +
                        "-fx-font-family: 'Poppins'; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 300; " +
                        "-fx-letter-spacing: -0.56px;");
            });


            circle.setOnMouseClicked(event -> {

                notificationManagement.deleteNotification(notification);

                VBoxlistNotification.getChildren().remove(pane);
                event.consume(); // Empêche la propagation du clic au pane
            });



            list.add(pane);


        }
        return list;
    }




    /**
     * openForm
     */
    public void openForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notif/NotificationForm.fxml"));
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
