package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import model.data.service.DPSManagement;
import model.data.persistence.DPS;
import model.data.persistence.Site;
import model.data.persistence.Sport;
import model.data.persistence.Journee;

public class EvenementController {

    @FXML
    VBox listEvent;

    @FXML
    Label countLabel;

    @FXML
    public void initialize() {
        DPSManagement dpsManagement = new DPSManagement();
        ArrayList<DPS>  listDPS = new ArrayList<>();
        Site site1 = new Site("S001", "Stade A", 2.35f, 48.85f);
        Site site2 = new Site("S002", "Gymnase B", 2.38f, 48.87f);

        Sport sport1 = new Sport("FB", "Football");
        Sport sport2 = new Sport("BB", "Basketball");

        Journee jour1 = new Journee(1, 6, 2025);
        Journee jour2 = new Journee(2, 6, 2025);

        listDPS.add(new DPS(1, "DPS 1", 900, 1200, site1, sport1, jour1));
        listDPS.add(new DPS(2, "DPS 2", 1300, 1600, site2, sport2, jour1));
        listDPS.add(new DPS(3, "DPS 3", 1000, 1500, site1, sport2, jour2));
        listDPS.add(new DPS(4, "DPS 4", 800, 1100, site2, sport1, jour2));
        listDPS.add(new DPS(5, "DPS 5", 800, 1100, site2, sport1, jour2));
        ArrayList<GridPane> list = listDPSToListGridPane(listDPS);
        listEvent.getChildren().clear();
        listEvent.getChildren().addAll(list);
        listEvent.setSpacing(15);
        listEvent.setStyle("-fx-background-color: #121215;");

        countLabel.setText(String.valueOf(listEvent.getChildren().size()));
    }

    private ArrayList<GridPane> listDPSToListGridPane(ArrayList<DPS> listDPS) {
        ArrayList<GridPane> list = new ArrayList<>();
        for (int i = 0; i < listDPS.size(); i++) {
            DPS dps = listDPS.get(i);
            Label label = new Label(dps.getName());
            label.setStyle("-fx-text-fill: white; -fx-font-size: 16px");

            Label date = new Label(dps.getJournee().getJour() + " " + dps.getJournee().getMois() + " de " + dps.getHoraireDepart() + " à " + dps.getHoraireFin());
            date.setStyle("-fx-text-fill: white; -fx-font-size: 12px");
            GridPane pane = new GridPane(1,2);
            GridPane subPane = new GridPane(2,1);
            subPane.add(label, 0, 0);
            subPane.add(date, 0, 1);
            pane.add(subPane,0,0);
            pane.setPrefHeight(60);
            pane.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 15;");
            label.setLayoutX(10);
            label.setLayoutY(10);
            list.add(pane);
        }
        return list;
    }
}
