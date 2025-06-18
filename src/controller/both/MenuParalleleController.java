package controller.both;

import controller.admin.EvenementController;
import controller.admin.FenetreGestionController;
import controller.admin.FenetreGestionInjectable;
import controller.both.MenuParalleleInjectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.io.IOException;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class MenuParalleleController {

    private FenetreGestionController fenetreGestionController;

    private EvenementController evenementController;

    private boolean isOpenSettings = false;

    @FXML
    private GridPane composentGrid;

    @FXML
    public void initialize() {
        System.out.println("MenuParalleleController initialized");
        setRow(0, "/fxml/commonwindow/DisplayProfil.fxml");
        setRow(1, "/fxml/commonwindow/DisplayCalendar.fxml");
        setRow(2, "/fxml/commonwindow/DisplayUpComingEvent.fxml");
    }

    @FXML
    public void linkToSettings() {
        if (!isOpenSettings) {
            isOpenSettings = true;
            if (getInstanceAuthentificationManagement().isAdmin()) {

                fenetreGestionController.loadContent2("/fxml/commonwindow/SettingsTemp.fxml");
                setRow(2, "/fxml/commonwindow/DisplayCalendarDisponibilites.fxml");
                setRow(1, null);
            } else {

                fenetreGestionController.loadContent2("/fxml/commonwindow/SettingsTemp.fxml");
                setRow(1, "/fxml/commonwindow/DisplayCalendarDisponibilites.fxml");
                setRow(2, null);


            }
        } else {
            isOpenSettings = false;

            if (fenetreGestionController != null) {
                if (getInstanceAuthentificationManagement().isAdmin()) {
                    fenetreGestionController.loadContent2("/fxml/adminwindow/DashboardEvent.fxml");
                } else {
                    fenetreGestionController.loadContent2("/fxml/commonwindow/DashboardCalendarAssignment.fxml");
                }
            }
            setRow(1, "/fxml/commonwindow/DisplayCalendar.fxml");
            setRow(2, "/fxml/commonwindow/DisplayUpComingEvent.fxml");
        }
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    void setRow(int rowIndex, String fxmlPath) {
        try {
            composentGrid.getChildren().removeIf(node ->
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex
            );

            if(fxmlPath != null) {



                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node node = loader.load();

                Object controller = loader.getController();
                if (controller instanceof MenuParalleleInjectable injectable) {
                    injectable.setMenuParalleleController(this);
                }

                if (controller instanceof ProfilController) {
                    ((ProfilController) controller).setMenuParalleleController(this);
                } else if (controller instanceof EvenementController) {
                    this.evenementController = (EvenementController) controller;
                } else if (controller instanceof SettingsController) {
                    ((SettingsController) controller).setMenuParalleleController(this);
                }

                composentGrid.add(node, 0, rowIndex);
                GridPane.setHalignment(node, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(node, javafx.geometry.VPos.BOTTOM);
                GridPane.setHgrow(node, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(node, javafx.scene.layout.Priority.ALWAYS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getComposentGrid() {
        return composentGrid;
    }

    public EvenementController getEvenementController() {
        return evenementController;
    }
}
