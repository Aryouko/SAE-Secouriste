package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/Calendar.fxml"));

        
        javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 19
        );

        stage.getIcons().add(new Image("/resources/images/logo.png"));
        stage.setTitle("SAE Secouriste");
        // stage.setMaximized(true);
        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

