package controller.both;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import static controller.UtilsController.linkToPage;

/**
 * ChargementController is responsible for handling the loading screen of the application.
 * It displays a loading screen for a few seconds before transitioning to the login page.
 */
public class ChargementController {

    /**
     * The AnchorPane that serves as the loading page.
     */
    @FXML
    private AnchorPane pageChargement;

    /**
     * Initializes the ChargementController by setting up a pause transition.
     * After 3 seconds, it links to the login page.
     */
    @FXML
    public void initialize() {

        System.out.println("ChargementController initialized");
        PauseTransition pause = new PauseTransition(Duration.seconds((Math.random() * 3) + 2));
        pause.setOnFinished(event -> linkToPage(pageChargement, "/fxml/auth/Connexion.fxml"));
        pause.play();
    }
}
