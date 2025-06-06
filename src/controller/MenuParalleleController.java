package controller;

import javafx.fxml.FXML;

public class MenuParalleleController {

    private FenetreGestionController fenetreGestionController;

    @FXML
    public void linkToSettings() {
        UtilsController.linkToPage(fenetreGestionController.getFenetreGestion(), "/fxml/Settings.fxml");
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }
}
