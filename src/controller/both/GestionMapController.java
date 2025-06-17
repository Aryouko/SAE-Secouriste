package controller.both;

import controller.UtilsController;
import controller.admin.FenetreGestionController;
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

    private FenetreGestionController fenetreGestionController;

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

    @FXML
    public void evenementButton() {
        fenetreGestionController.loadContent2("/fxml/admin/GestionEvenement.fxml");
    }

    @FXML
    public void calendarAssignment() {
        fenetreGestionController.loadContent2("/fxml/both/CalendarAssignment.fxml");
    }


    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }
}
