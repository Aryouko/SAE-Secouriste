package controller.both;

import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import static controller.UtilsController.linkToPage;

public class GestionMapController {

    @FXML
    private FlowPane carteFlowPane;

    @FXML
    private AnchorPane gestionCarte;

    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/both/Map.fxml"));
            AnchorPane page = loader.load();
            carteFlowPane.getChildren().setAll(page);

            // Forcer la carte à prendre toute la taille du FlowPane
            page.prefWidthProperty().bind(carteFlowPane.widthProperty());
            page.prefHeightProperty().bind(carteFlowPane.heightProperty());
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }
    }

    // ////// BUTTON

    public void evenementButton() {
        linkToPage(gestionCarte, "fxml/admin/GestionEvenement.fxml");
    }

    public void calendarAssignment() {
        linkToPage(gestionCarte, "fxml/both/CalendarAssignment.fxml");
    }


}
