package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.data.service.DPSManagement;
import model.data.persistence.DPS;
import model.data.persistence.Site;
import model.data.persistence.Sport;
import model.data.persistence.Journee;

public class EvenementController {

    @FXML
    private VBox listEvent;

    @FXML
    private Label countLabel;

    @FXML
    public void initialize() {
        DPSManagement dpsManagement = new DPSManagement();
        ArrayList<DPS>  listDPS = new ArrayList<>();
        Site site1 = new Site(1, "Stade A", 2.35f, 48.85f);
        Site site2 = new Site(2, "Gymnase B", 2.38f, 48.87f);

        Sport sport1 = new Sport(1, "Football");
        Sport sport2 = new Sport(2, "Basketball");

        Journee jour1 = new Journee(1, 6, 2025);
        Journee jour2 = new Journee(13, 6, 2025);

        listDPS.add(new DPS(1, "DPS 1", 9, 12, site1, sport1, jour1));
        listDPS.add(new DPS(2, "DPS 2", 13, 16, site2, sport2, jour1));
        listDPS.add(new DPS(3, "DPS 3", 10, 15, site1, sport2, jour2));
        listDPS.add(new DPS(4, "DPS 4", 8, 11, site2, sport1, jour2));
        listDPS.add(new DPS(5, "DPS 5", 8, 11, site2, sport1, jour2));
        ArrayList<GridPane> list = listDPSToListGridPane(listDPS);
        listEvent.getChildren().clear();
        listEvent.getChildren().addAll(list);
        listEvent.setSpacing(10);
        listEvent.setStyle("-fx-background-color: #121215;");

        countLabel.setText(String.valueOf(listEvent.getChildren().size()));
    }

    private ArrayList<GridPane> listDPSToListGridPane(ArrayList<DPS> listDPS) {
        ArrayList<GridPane> list = new ArrayList<>();
        for (int i = 0; i < listDPS.size(); i++) {
            DPS dps = listDPS.get(i);
            Label label = new Label(dps.getName());
            label.setStyle("-fx-text-fill: white; -fx-font-size: 16px");
            String[] months = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };

            Label date = new Label(dps.getJournee().getJour() + " " + months[dps.getJournee().getMois() - 1] + " de " + dps.getHoraireDepart() + "h à " + dps.getHoraireFin() + "h");
            date.setStyle("-fx-text-fill: white; -fx-font-size: 12px");

            Circle circle = new Circle();
            circle.setRadius(15);
            LocalDate today = LocalDate.now();
            LocalDate eventDate = LocalDate.of(dps.getJournee().getAnnee(), dps.getJournee().getMois(), dps.getJournee().getJour());
            long daysBetween = ChronoUnit.DAYS.between(today, eventDate);
            if (daysBetween < 7) {
                circle.setFill(Color.web("#3EAD42"));
            } else {
                circle.setFill(Color.web("#4A4AE4"));
            }

            GridPane pane = new GridPane();
            GridPane subPane = new GridPane();

            ColumnConstraints col = new ColumnConstraints();
            col.setMaxWidth(500);
            col.setPrefWidth(500);
            pane.getColumnConstraints().addAll(col);

            subPane.add(label, 0, 0);
            subPane.add(date, 0, 1);
            pane.add(subPane,0,0);
            pane.add(circle,1,0);
            pane.setPrefHeight(60);
            pane.setPadding(new Insets(10));
            VBox.setMargin(pane, new Insets(0,10,0,0));
            pane.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 15");
            label.setLayoutX(10);
            label.setLayoutY(10);
            list.add(pane);
        }
        return list;
    }
}
