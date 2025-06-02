package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("FXML URL = " + getClass().getResource("/fxml/Calendar.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Calendar.fxml"));


        javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 19
        );

        stage.setTitle("SAE Secouriste");
        stage.setScene(new Scene(root, 1200, 720));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

