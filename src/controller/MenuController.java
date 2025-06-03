package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import model.data.persistence.DPS;
import model.data.persistence.Journee;
import model.data.persistence.Site;
import model.data.persistence.Sport;
import model.data.service.DPSManagement;

import java.util.ArrayList;

public class MenuController {

    @FXML
    private TilePane evenementTile;

    @FXML
    public void initialize() {

        evenementTile.setHgap(50);
        evenementTile.setVgap(50);
        evenementTile.setStyle("-fx-padding: 50;");
        evenementTile.setMaxWidth(1350);

        String[] months = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };

        DPSManagement dpsManagement = new DPSManagement();
        ArrayList<DPS> listDPS = dpsManagement.getListDPS();

        Site site1 = new Site("S001", "Stade A", 2.35f, 48.85f);
        Site site2 = new Site("S002", "Gymnase B", 2.38f, 48.87f);

        Sport sport1 = new Sport(1, "Football");
        Sport sport2 = new Sport(2, "Basketball");

        Journee jour1 = new Journee(1, 6, 2025);
        Journee jour2 = new Journee(13, 6, 2025);

        listDPS.add(new DPS(1, "DPS 1", 9, 12, site1, sport1, jour1));
        listDPS.add(new DPS(2, "DPS 2", 13, 16, site2, sport2, jour1));
        listDPS.add(new DPS(3, "DPS 3", 10, 15, site1, sport2, jour2));
        listDPS.add(new DPS(4, "DPS 4", 8, 11, site2, sport1, jour2));
        listDPS.add(new DPS(5, "DPS 5", 8, 11, site2, sport1, jour2));

        for (DPS dps : listDPS) {
            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(369, 376);
            gridPane.setMaxSize(369, 376);
            gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-border-color: #A0A0A0; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

            Label sport = new Label(dps.getSport().getNom());
            sport.setStyle("-fx-font-size: 32;");

            Label site = new Label(dps.getSite().getNom());
            site.setStyle("-fx-font-size: 12;");

            GridPane subSubGridPane1 = new GridPane();
            subSubGridPane1.setPrefSize(230, 22);
            subSubGridPane1.setMaxSize(230,22);

            Label jour = new Label(dps.getJournee().getJour() + " " + months[dps.getJournee().getMois() - 1] + " : ");
            Label dep = new Label(dps.getHoraireDepart() + "h");
            Label entre = new Label(" au ");
            Label fin = new Label(dps.getHoraireFin() + "h");
            dep.setMinWidth(40);
            fin.setMinWidth(40);
            dep.setAlignment(Pos.CENTER);
            fin.setAlignment(Pos.CENTER);
            dep.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2px; -fx-padding: 4;");
            fin.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 2px; -fx-padding: 4;");

            subSubGridPane1.add(jour,0,0);
            subSubGridPane1.add(dep,1,0);
            subSubGridPane1.add(entre,2,0);
            subSubGridPane1.add(fin,3,0);

            GridPane subGridPane1 = new GridPane();
            subGridPane1.setPrefSize(354, 305);
            subGridPane1.setMaxSize(354, 305);
            subGridPane1.setStyle("-fx-background-color: #B9D9FF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");

            subGridPane1.add(sport,0,0);
            subGridPane1.add(site,0,1);
            subGridPane1.add(subSubGridPane1,0,2);

            GridPane subGridPane2 = new GridPane();
            subGridPane2.setPrefSize(354, 71);
            subGridPane2.setMaxSize(354, 71);

            gridPane.add(subGridPane1, 0, 0);
            gridPane.add(subGridPane2, 0, 1);

            evenementTile.getChildren().add(gridPane);
        }

        for (int i = 0; i < (3 - listDPS.size() % 3); i++) {
            GridPane emptyPane = new GridPane();
            emptyPane.setPrefSize(369, 367);
            emptyPane.setMaxSize(369, 376);
            emptyPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");
            evenementTile.getChildren().add(emptyPane);
        }
    }
}
