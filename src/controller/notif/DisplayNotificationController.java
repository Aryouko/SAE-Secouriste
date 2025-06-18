package controller.notif;

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
import model.data.service.DPSManagement;
import model.data.service.NotificationManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayNotificationController {

    /**
     * FXML elements for the notification view.
     */
    @FXML
    private ScrollPane scrollPaneNotification ;

    /**
     * Button to open the notification form.
     */
    @FXML
    private Button openFormButton ;

    /**
     * VBox to list notifications.
     */
    @FXML
    private VBox VBoxlistNotification;

    /**
     * Instance of NotificationManagement to handle notification operations.
     */
    private final NotificationManagement notificationManagement = new NotificationManagement();

    /**
     * Instance of AuthentificationManagement to manage user authentication.
     */
    private final AuthentificationManagement auth = AuthentificationManagement.getInstanceAuthentificationManagement();

    /**
     * Initializes the controller and loads notifications for the current user.
     * Sets up the UI elements based on user permissions (admin or rescuer).
     */
    @FXML
    public void initialize() {
        // Get the current user ID
        long id = auth.getCurrentUser().getIdUser() ;

        // Delete the possibilities to send message if you are a rescuer
        openFormButton.setVisible(auth.isAdmin());
        openFormButton.setDisable(!auth.isAdmin());

        // Load notifications for the current user - admin or rescuer
        // Admins see all notifications, rescuers see only their own
        List<Notification> listNotification = this.notificationManagement.getNotificationByIdSender(id);

        // Convert the list of notifications to a list of GridPanes for display
        ArrayList<GridPane> list = listNotificationToListGridPane(listNotification);
        VBoxlistNotification.getChildren().clear();
        VBoxlistNotification.getChildren().addAll(list);
        VBoxlistNotification.setSpacing(10);
        VBoxlistNotification.setStyle("-fx-background-color: #121215;");
    }


    /**
     * Creates a list of GridPanes from a list of Notification objects.
     * Each GridPane represents a notification with its title, date, and associated DPS.
     *
     * @param listNotification List of Notification objects to convert into GridPanes.
     * @return ArrayList of GridPane objects, each representing a notification.
     */
    private ArrayList<GridPane> listNotificationToListGridPane(List<Notification> listNotification) {

        ArrayList<GridPane> list = new ArrayList<>();
        DPSManagement dpsManagement = new model.data.service.DPSManagement(); // Instance to manage DPS data

        for (int i = 0; i < listNotification.size(); i++) { // Loop through each notification
            Notification notification = listNotification.get(i);

            Label title = new Label(notification.getTitle()); // Create a label for the notification title
            title.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

            String dpsName = dpsManagement.getDpsById(notification.getIdDPS()).getName(); // Get the name of the DPS associated with the notification

            Label dpsLabel = new Label(dpsName); // Create a label for the DPS name

            Label date = new Label(notification.getDate()); // Create a label for the notification date

            Label infosLabel = new Label(dpsName + " à " + notification.getDate()); // Create a label for additional information (DPS name and date)
            infosLabel.setStyle("-fx-text-fill: #EDF2F66F; " + // Style the label with a specific color and font
                    "-fx-font-family: 'Poppins'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: 300; " +
                    "-fx-letter-spacing: -0.56px;");

            Circle circle = new Circle();
            circle.setRadius(20);
            circle.setFill(javafx.scene.paint.Color.web("#4A4AE4"));



            GridPane pane = new GridPane(); // Create a GridPane to hold the notification elements
            GridPane subPane = new GridPane();

            subPane.setPadding(new Insets(0, 0, 0, 15));

            ColumnConstraints col = new ColumnConstraints();
            col.setMaxWidth(500);
            col.setPrefWidth(500);
            pane.getColumnConstraints().addAll(col);

            subPane.add(title, 0, 0);
            subPane.add(infosLabel, 0, 1);
            pane.add(subPane,0,0);
            pane.add(circle,1,0);

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

            title.setLayoutX(10);
            title.setLayoutY(10);


            // Set up mouse click event handlers for the pane and circle
            pane.setOnMouseClicked(event -> {
                try {
                    pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 50");
                    title.setStyle("-fx-text-fill: #000000; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

                    infosLabel.setStyle("-fx-text-fill: rgb(0,0,0); " +
                            "-fx-font-family: 'Poppins'; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 300; " +
                            "-fx-letter-spacing: -0.56px;");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notif/PopupNotificationReader.fxml"));
                    Parent root = loader.load();
                    PopupNotificationReaderController controller = loader.getController();
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
                title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-family: 'Poppins';");

                infosLabel.setStyle("-fx-text-fill: #EDF2F66F ; " +
                        "-fx-font-family: 'Poppins'; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 300; " +
                        "-fx-letter-spacing: -0.56px;");
            });

            // Circle click event to delete the notification
            circle.setOnMouseClicked(event -> {

                notificationManagement.deleteNotification(notification);
                VBoxlistNotification.getChildren().remove(pane);
                event.consume(); // Prevent further propagation of the event
            });
            list.add(pane); // Add the pane to the list of GridPanes
        }
        return list;
    }


    /**
     * Opens a popup form for creating a new notification.
     *
     * @throws IOException If there is an error loading the FXML file for the popup form.
     */
    public void openForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notif/PopupNotificationForm.fxml"));
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
