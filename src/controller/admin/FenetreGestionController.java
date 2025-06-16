package controller.admin;

import controller.both.CalendarAssignmentController;
import controller.both.MenuParalleleController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class FenetreGestionController {

    @FXML
    private StackPane includePane1;

    @FXML
    private StackPane includePane2;

    @FXML
    private AnchorPane fenetreGestion;

    private static AnchorPane staticFenetreGestion;

    private GestionEvenementController gestionEvenementController;

    private GestionSecouristeController gestionSecouristeController;

    private CalendarAssignmentController calendarAssignmentController;

    private MenuParalleleController menuParalleleController;

    @FXML
    public void initialize() {
        staticFenetreGestion = fenetreGestion;
        loadContent1("/fxml/both/MenuParallele.fxml");
        if (getInstanceAuthentificationManagement().isAdmin()) {
            loadContent2("/fxml/admin/GestionEvenement.fxml");
        } else {
            loadContent2("/fxml/both/CalendarAssignment.fxml");
        }
    }

    public void loadContent1(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            this.includePane1.getChildren().setAll(newContent);
            Object controller = loader.getController();

            this.menuParalleleController = (MenuParalleleController) controller;
            ((MenuParalleleController)controller).setFenetreGestionController(this);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de " + fxmlFile + " : " + e.getMessage());
            e.printStackTrace();
            controller.UtilsController.showError("Erreur lors du chargement de " + fxmlFile + " : " + e.getMessage());
        }
    }

    public void loadContent2(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();
            this.includePane2.getChildren().setAll(newContent);

            Object controller = loader.getController();
            if (controller instanceof GestionEvenementController) {
                this.gestionEvenementController = (GestionEvenementController) controller;
                ((GestionEvenementController) controller).setFenetreGestionController(this);
                ((GestionEvenementController) controller).setEvenementController(this.menuParalleleController.getEvenementController());
            } else if (controller instanceof GestionSecouristeController) {
                this.gestionSecouristeController = (GestionSecouristeController) controller;
                ((GestionSecouristeController)controller).setFenetreGestionController(this);
            } else if (controller instanceof CalendarAssignmentController) {
                this.calendarAssignmentController = (CalendarAssignmentController) controller;
                ((CalendarAssignmentController)controller).setFenetreGestionController(this);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de " + fxmlFile + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public AnchorPane getFenetreGestion() {
        return this.fenetreGestion;
    }


    public static StackPane showOverlay() {
        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayPane.setId("fenetreGestionOverlay");
        AnchorPane.setTopAnchor(overlayPane, 0.0);
        AnchorPane.setBottomAnchor(overlayPane, 0.0);
        AnchorPane.setLeftAnchor(overlayPane, 0.0);
        AnchorPane.setRightAnchor(overlayPane, 0.0);
        staticFenetreGestion.getChildren().add(overlayPane);
        return overlayPane;
    }

    public static void removeOverlay() {
        staticFenetreGestion.getChildren().removeIf(node ->
            node instanceof StackPane && "fenetreGestionOverlay".equals(node.getId())
        );
    }
}
