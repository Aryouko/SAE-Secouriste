package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import model.dao.PossessionDAO;
import model.dao.SecouristeDAO;
import model.data.persistence.*;
import model.data.service.SecouristeManagement;

import java.util.ArrayList;
import java.util.List;

public class GestionSecouristeController {

    @FXML
    private TilePane evenementTile;

    @FXML
    private ComboBox<String> grpComboBox;

    @FXML
    private ComboBox<String> certComboBox;

    @FXML
    private ComboBox<String> attCertComboBox;

    private ArrayList<String> groupes;

    private ArrayList<String> certifications;

    private ArrayList<String> attCertifications;

    private List<Secouriste> secouristeList;

    private FenetreGestionController fenetreGestionController;

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    @FXML
    public void initialize() {

        this.groupes = new ArrayList<>();
        this.certifications = new ArrayList<>();
        this.attCertifications = new ArrayList<>();

        this.evenementTile.setHgap(50);
        this.evenementTile.setVgap(50);
        this.evenementTile.setStyle("-fx-padding: 50;");
        this.evenementTile.setMaxWidth(1350);

        this.secouristeList = secouristeManagement.getSecouristes();

        tileInitialize(this.secouristeList);
        comboBoxInitialize();
    }

    private void tileInitialize(List<Secouriste> listSecouristes) {

        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        if (listSecouristes != null) {
            for (Secouriste secouriste : listSecouristes) {
                PossessionDAO possessionDAO = new PossessionDAO();

                Possession possession = possessionDAO.find(secouriste);
                if (possession != null) {
                    for (Competence competence : possession.getCompetencesSec()) {
                        if (!this.certifications.contains(competence.getIntitule())) {
                            this.certifications.add(competence.getIntitule());
                        }
                    }
                }

                GridPane gridPane = new GridPane();
                gridPane.setPrefSize(369, 376);
                gridPane.setMaxSize(369, 376);
                gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-border-color: #A0A0A0; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

                Label personne = new Label(secouriste.getNom() + " " + secouriste.getPrenom());
                personne.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

                GridPane subSubGridPane1 = new GridPane();
                subSubGridPane1.setPrefSize(230, 22);
                subSubGridPane1.setMaxSize(230, 22);

                Label certif = new Label("Certifications :");
                certif.setStyle("-fx-text-fill: #FFFFFF;");

                int row = 0;
                int col = 0;
                for (Competence competence : possession.getCompetencesSec()) {
                    Label certLabel = new Label(competence.getIntitule());
                    certLabel.setMinWidth(50);
                    certLabel.setAlignment(Pos.CENTER);
                    GridPane.setMargin(certLabel, new Insets(5));
                    certLabel.setStyle("-fx-border-radius: 20; -fx-border-color: #FFFFFF; -fx-text-fill: #FFFFFF; -fx-border-width: 2px; -fx-padding: 6;");
                    if (row == 3) {
                        row = 0;
                        col++;
                    }
                    subSubGridPane1.add(certLabel, row, col);
                    row++;
                }

                for (int i = 0; i < col % 3; i++) {
                    Label certLabel = new Label();
                    certLabel.setMinWidth(50);
                    certLabel.setAlignment(Pos.CENTER);
                    subSubGridPane1.add(certLabel, row, i);
                }

                GridPane subGridPane1 = new GridPane();
                subGridPane1.setPrefSize(354, 305);
                subGridPane1.setMaxSize(354, 305);
                subGridPane1.setStyle("-fx-background-color: #FF4747; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");

                subGridPane1.add(personne, 0, 0);
                subGridPane1.add(certif, 0, 1);
                subGridPane1.add(subSubGridPane1, 0, 2);

                GridPane subGridPane2 = new GridPane();
                subGridPane2.setPrefSize(354, 71);
                subGridPane2.setMaxSize(354, 71);

                gridPane.add(subGridPane1, 0, 0);
                gridPane.add(subGridPane2, 0, 1);

                this.evenementTile.getChildren().add(gridPane);
            }

            for (int i = 0; i < (3 - listSecouristes.size() % 3); i++) {
                GridPane emptyPane = new GridPane();
                emptyPane.setPrefSize(369, 367);
                emptyPane.setMaxSize(369, 376);
                emptyPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");
                this.evenementTile.getChildren().add(emptyPane);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                GridPane emptyPane = new GridPane();
                emptyPane.setPrefSize(369, 367);
                emptyPane.setMaxSize(369, 376);
                emptyPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");
                this.evenementTile.getChildren().add(emptyPane);
            }
        }
    }

    private void comboBoxInitialize() {
        this.grpComboBox.getItems().add("Groupe d'affectation");
        this.certComboBox.getItems().add("Certification");
        this.attCertComboBox.getItems().add("Certification en attente");
        this.grpComboBox.getSelectionModel().select("Groupe d'affectation");
        this.certComboBox.getSelectionModel().select("Certification");
        this.attCertComboBox.getSelectionModel().select("Certification en attente");
        this.grpComboBox.getItems().addAll(this.groupes);
        this.certComboBox.getItems().addAll(this.certifications);
        this.attCertComboBox.getItems().addAll(this.attCertifications);
    }

    @FXML
    public void filtreUpdate() {
        this.evenementTile.getChildren().clear();
        ArrayList<Secouriste> listSecouriste = new ArrayList<>();
        for (Secouriste secouriste : this.secouristeList) {
            PossessionDAO possessionDAO = new PossessionDAO();
            boolean verifCert = false;
            for (Competence competence : possessionDAO.find(secouriste).getCompetencesSec()) {
                if (competence.getIntitule().equals(this.certComboBox.getSelectionModel().getSelectedItem())) {
                    verifCert = true;
                }
            }
            if (verifCert || this.certComboBox.getSelectionModel().getSelectedItem().equals("Certification")) {
                listSecouriste.add(secouriste);
            }

        }
        tileInitialize(listSecouriste);
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    @FXML
    public void GestionEvenementButton() {
        this.fenetreGestionController.loadContent2("/fxml/GestionEvenement.fxml");
    }
}
