package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import javafx.scene.shape.Rectangle;
import model.data.persistence.DPS;
import model.data.persistence.Journee;
import model.data.persistence.Site;
import model.data.persistence.Sport;
import model.data.service.DPSManagement;

import java.util.ArrayList;

public class GestionEvenementController {

    @FXML
    private TilePane evenementTile;

    @FXML
    private ComboBox<String> sitesComboBox;

    @FXML
    private ComboBox<String> sportsComboBox;

    private ArrayList<String> sports;

    private ArrayList<String> sites;

    private ArrayList<DPS> dpsList;

    private FenetreGestionController fenetreGestionController;

    @FXML
    public void initialize() {

        this.sports = new ArrayList<>();
        this.sites = new ArrayList<>();
        this.dpsList = new ArrayList<>();

        this.evenementTile.setHgap(50);
        this.evenementTile.setVgap(50);
        this.evenementTile.setStyle("-fx-padding: 50;");
        this.evenementTile.setMaxWidth(1350);

        DPSManagement dpsManagement = new DPSManagement();
        this.dpsList = dpsManagement.getListDPS();

        Site site1 = new Site(1, "Stade A", 2.35f, 48.85f);
        Site site2 = new Site(2, "Gymnase B", 2.38f, 48.87f);

        Sport sport1 = new Sport(1, "Football");
        Sport sport2 = new Sport(2, "Basketball");

        Journee jour1 = new Journee(1, 6, 2025);
        Journee jour2 = new Journee(13, 6, 2025);

        this.dpsList.add(new DPS(1, "DPS 1", 9, 12, site1, sport1, jour1));
        this.dpsList.add(new DPS(2, "DPS 2", 13, 16, site2, sport2, jour1));
        this.dpsList.add(new DPS(3, "DPS 3", 10, 15, site1, sport2, jour2));
        this.dpsList.add(new DPS(4, "DPS 4", 8, 11, site2, sport1, jour2));
        this.dpsList.add(new DPS(5, "DPS 5", 8, 11, site2, sport1, jour2));

        tileInitialize(this.dpsList);
        comboBoxInitialize();
    }

    private void tileInitialize(ArrayList<DPS> listDPS) {

        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (DPS dps : listDPS) {
            if (!this.sports.contains(dps.getSport().getNom())) {
                this.sports.add(dps.getSport().getNom());
            }

            if (!this.sites.contains(dps.getSite().getNom())) {
                this.sites.add(dps.getSite().getNom());
            }

            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(369, 376);
            gridPane.setMaxSize(369, 376);
            gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-border-color: #A0A0A0; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

            Label titre = new Label(dps.getName());
            titre.setStyle("-fx-font-size: 32;");

            Label sport = new Label("Sport : " + dps.getSport().getNom());
            sport.setStyle("-fx-font-size: 14;");

            Label site = new Label("Lieu : " + dps.getSite().getNom());
            site.setStyle("-fx-font-size: 14;");

            GridPane subSubGridPane1 = new GridPane();
            subSubGridPane1.setPrefSize(230, 22);
            subSubGridPane1.setMaxSize(230,22);

            Label jour = new Label(dps.getJournee().getJour() + " " + months[dps.getJournee().getMois() - 1] + " : ");
            Label dep = new Label(dps.getHoraireDepart() + "h");
            Label entre = new Label("au");
            Label fin = new Label(dps.getHoraireFin() + "h");
            jour.setMinWidth(50);
            dep.setMinWidth(60);
            entre.setMinWidth(30);
            fin.setMinWidth(60);
            dep.setAlignment(Pos.CENTER);
            entre.setAlignment(Pos.CENTER);
            fin.setAlignment(Pos.CENTER);
            jour.setStyle("-fx-font-size: 14;");
            dep.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2px; -fx-padding: 6; -fx-font-size: 14;");
            entre.setStyle("-fx-font-size: 14;");
            fin.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2px; -fx-padding: 6; -fx-font-size: 14;");

            subSubGridPane1.add(jour,0,0);
            subSubGridPane1.add(dep,1,0);
            subSubGridPane1.add(entre,2,0);
            subSubGridPane1.add(fin,3,0);

            GridPane subGridPane1 = new GridPane();
            subGridPane1.setPrefSize(354, 305);
            subGridPane1.setMaxSize(354, 305);
            subGridPane1.setStyle("-fx-background-color: #B9D9FF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");

            subGridPane1.add(titre,0,0);
            subGridPane1.add(sport,0,1);
            subGridPane1.add(site,0,2);
            subGridPane1.add(subSubGridPane1,0,3);

            GridPane subGridPane2 = new GridPane();
            subGridPane2.setPrefSize(354, 71);
            subGridPane2.setMaxSize(354, 71);

            gridPane.add(subGridPane1, 0, 0);
            gridPane.add(subGridPane2, 0, 1);

            this.evenementTile.getChildren().add(gridPane);
        }

        for (int i = 0; i < (3 - listDPS.size() % 3); i++) {
            GridPane emptyPane = new GridPane();
            emptyPane.setPrefSize(369, 367);
            emptyPane.setMaxSize(369, 376);
            emptyPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");
            this.evenementTile.getChildren().add(emptyPane);
        }
    }

    private void comboBoxInitialize() {
        this.sitesComboBox.getItems().add("Site");
        this.sportsComboBox.getItems().add("Sport");
        this.sitesComboBox.getSelectionModel().select("Site");
        this.sportsComboBox.getSelectionModel().select("Sport");
        this.sitesComboBox.getItems().addAll(this.sites);
        this.sportsComboBox.getItems().addAll(this.sports);
    }

    @FXML
    public void filtreUpdate() {
        this.evenementTile.getChildren().clear();
        ArrayList<DPS> listDPS = new ArrayList<>();
        for (DPS dps : this.dpsList)  {
            if ((dps.getSite().getNom().equals(this.sitesComboBox.getSelectionModel().getSelectedItem())
                    || this.sitesComboBox.getSelectionModel().getSelectedItem().equals("Site"))
                    && (dps.getSport().getNom().equals(this.sportsComboBox.getSelectionModel().getSelectedItem())
                    || this.sportsComboBox.getSelectionModel().getSelectedItem().equals("Sport"))) {
                listDPS.add(dps);
            }
        }
        tileInitialize(listDPS);
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    @FXML
    public void GestionSecouristeButton() {
        this.fenetreGestionController.loadContent("/fxml/GestionSecouriste.fxml");
    }
}
