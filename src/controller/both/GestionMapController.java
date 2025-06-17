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
            FXMLLoader loader = new FXMLLoader(UtilsController.class.getResource("fxml/both/GestionMap.fxml"));
            AnchorPane page = loader.load();
            carteFlowPane.getChildren().setAll(page);
            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 0.0);
            AnchorPane.setLeftAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }

    }

    // ////// BUTTON

    public void evenementButton() {
        linkToPage(gestionCarte, "fxml/admin/GestionEvenement.fxml");
    }

    public void calendarAssignment() {
        linkToPage(gestionCarte, "fxml/admin/CalendarAssignment.fxml");
    }


}
