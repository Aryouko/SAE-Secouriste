package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class MenuParalleleController {

    private FenetreGestionController fenetreGestionController;


    @FXML
    private GridPane composentGrid;

    @FXML
    public void initialize() {
        setRow(0, "/fxml/Profil.fxml");
        setRow(1, "/fxml/Calendar.fxml");
        setRow(2, "/fxml/Evenement.fxml");
    }

    @FXML
    public void linkToSettings() {
        UtilsController.linkToPage(fenetreGestionController.getFenetreGestion(), "/fxml/Settings.fxml");
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    void setRow(int rowIndex, String fxmlPath) {
        try {
            composentGrid.getChildren().removeIf(node ->
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex
            );

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();
            Object controller = loader.getController();
            if (controller instanceof ProfilController) {
                ((ProfilController) controller).setMenuParallelController(this);
            }
            composentGrid.add(node, 0, rowIndex);
            GridPane.setHalignment(node, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(node, javafx.geometry.VPos.BOTTOM);
            GridPane.setHgrow(node, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setVgrow(node, javafx.scene.layout.Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getComposentGrid() {
        return composentGrid;
    }
}
