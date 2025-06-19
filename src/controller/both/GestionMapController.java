package controller.both;

import controller.admin.FenetreGestionController;
import controller.admin.FenetreGestionInjectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class GestionMapController implements FenetreGestionInjectable {


    @FXML
    private FlowPane carteFlowPane;

    @FXML
    private AnchorPane gestionCarte;

    @FXML
    private AnchorPane carteAnchorPane;

    @FXML
    private Button planningButton;

    @FXML
    private Button gestionButton;


    private FenetreGestionController fenetreGestionController;

    @FXML
    public void initialize() {
        try {
            if (getInstanceAuthentificationManagement().isAdmin()) {
                planningButton.setVisible(true);
                planningButton.disableProperty().set(false);
            } else {
                gestionButton.setText("Retour au planning");
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/common/map/Map.fxml"));
            AnchorPane page = loader.load();
            carteAnchorPane.getChildren().setAll(page);

            Rectangle clip = new Rectangle();
            clip.setArcWidth(60);
            clip.setArcHeight(60);
            clip.widthProperty().bind(page.widthProperty());
            clip.heightProperty().bind(page.heightProperty());

            page.setClip(clip);
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }
    }



    // ////// BUTTON

    @FXML
    public void evenementButton() {
        if (getInstanceAuthentificationManagement().isAdmin()) {
            fenetreGestionController.loadContent2("/fxml/admin/DashboardEvent.fxml");
        } else {
            calendarAssignment();
        }
    }

    @FXML
    public void calendarAssignment() {
        fenetreGestionController.loadContent2("/fxml/common/DashboardCalendarAssignment.fxml");
    }


    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }
}
