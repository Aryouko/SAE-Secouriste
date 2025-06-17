package controller.both;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.Node;
import model.data.persistence.Disponibilite;
import model.data.persistence.Journee;
import model.data.service.SecouristeManagement;
import model.data.service.DisponibiliteManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalendarDisponibilitesController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    private AnchorPane settingsPane;

    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();
    private model.data.persistence.Secouriste sec;
    private int mois;
    private int annee;
    private final Set<LocalDate> disponibilites = new HashSet<>();
    private final Map<Label, LocalDate> labelDateMap = new HashMap<>();
    private final List<Label> selectedLabels = new ArrayList<>();
    private final Set<LocalDate> tempSelection = new HashSet<>();
    private LocalDate selectionStart = null;
    private LocalDate selectionEnd = null;

    @FXML
    private Label jourLabel;

    @FXML
    private void initialize() {
        LocalDate today = LocalDate.now();
        this.mois = today.getMonthValue();
        this.annee = today.getYear();
        try {
            sec = new SecouristeManagement().getSecouristeById(model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            chargerDisponibilites();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        populateCalendar(this.annee, this.mois);
    }

    private void chargerDisponibilites() {
        disponibilites.clear();
        ArrayList<Disponibilite> dispoList = disponibiliteManagement.getDisponibilites(sec);
        for (Disponibilite dispo : dispoList) {
            Journee journee = dispo.getJourDisp();
            LocalDate date = LocalDate.of(journee.getAnnee(), journee.getMois(), journee.getJour());
            disponibilites.add(date);
        }
    }

    @FXML
    private void moisSuivant() {
        if (this.mois == 12) {
            this.annee++;
            this.mois = 1;
            calendarGrid.getChildren().clear();
            populateCalendar(this.annee, this.mois);
        } else {
            calendarGrid.getChildren().clear();
            this.mois++;
            populateCalendar(this.annee, this.mois);
        }
    }

    @FXML
    private void moisPrecedent() {
        if (this.mois == 1) {
            calendarGrid.getChildren().clear();
            this.mois = 12;
            this.annee--;
            populateCalendar(this.annee, this.mois);
        } else {
            calendarGrid.getChildren().clear();
            this.mois--;
            populateCalendar(this.annee, this.mois);
        }
    }

    private void populateCalendar(int annee, int mois) {
        calendarGrid.getChildren().clear();
        labelDateMap.clear();
        selectedLabels.clear();
        tempSelection.clear();
        String[] months = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };
        jourLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 24px");
        jourLabel.setText(months[mois - 1] + " " + annee);
        String[] days = { "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim" };
        for (int i = 0; i < days.length; i++) {
            Label dayHeaderLabel = new Label(days[i]);
            dayHeaderLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px");
            calendarGrid.add(dayHeaderLabel, i, 0);
        }
        java.time.YearMonth yearMonth = java.time.YearMonth.of(annee, mois);
        LocalDate firstDay = yearMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        int col = dayOfWeek - 1;
        int row = 1;
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(annee, mois, day);
            Label dayLabel = new Label(String.valueOf(day));
            applyDayStyle(dayLabel, date);
            dayLabel.setMinSize(40, 40);
            dayLabel.setMaxSize(40, 40);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setFocusTraversable(true);
            dayLabel.setPickOnBounds(true);
            labelDateMap.put(dayLabel, date);
            setupMouseHandlers(dayLabel);
            calendarGrid.add(dayLabel, col, row);
            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
        for (Node node : calendarGrid.getChildren()) {
            if (node instanceof Label label) {
                LocalDate date = labelDateMap.get(label);
                if (date != null && date.equals(LocalDate.now())) {
                    calendarGrid.getChildren().remove(label);
                    calendarGrid.getChildren().add(label);
                    break;
                }
            }
        }
        calendarGrid.setOnMouseDragged(e -> {
            Node node = e.getPickResult().getIntersectedNode();
            while (node != null && !(node instanceof Label)) {
                node = node.getParent();
            }
            if (node instanceof Label label && labelDateMap.containsKey(label)) {
                selectionEnd = labelDateMap.get(label);
                updateSelectionVisuals();
            }
        });
    }

    private void applyDayStyle(Label dayLabel, LocalDate date) {
        LocalDate today = LocalDate.now();
        boolean isToday = date.equals(today);
        boolean isDisponible = disponibilites.contains(date);
        if (isToday && isDisponible) {
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 5;-fx-background-insets: 1; -fx-background-color: #30308f; -fx-font-size: 16px");
        } else if (isToday) {
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 5;-fx-background-insets: 1; -fx-background-color: #4A4AE4; -fx-font-size: 16px");
        } else if (isDisponible) {
            dayLabel.setStyle("-fx-padding: 0;-fx-background-insets: 1; -fx-background-color: #7272ff; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 16px");
        } else {
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px");
        }
    }

    private void setupMouseHandlers(Label dayLabel) {
        dayLabel.setOnMousePressed(e -> {
            LocalDate date = labelDateMap.get(dayLabel);
            if (date != null) {
                selectionStart = date;
                selectionEnd = date;
                updateSelectionVisuals();
            }
        });
        dayLabel.setOnMouseReleased(e -> {
            if (selectionStart != null && selectionEnd != null) {
                LocalDate start = selectionStart.isBefore(selectionEnd) ? selectionStart : selectionEnd;
                LocalDate end = selectionStart.isAfter(selectionEnd) ? selectionStart : selectionEnd;
                traiterSelection(start, end);
                clearTempSelection();
            }
        });
    }

    private void traiterSelection(LocalDate start, LocalDate end) {
        Set<LocalDate> periodeSelectionnee = new HashSet<>();
        LocalDate current = start;
        while (!current.isAfter(end)) {
            periodeSelectionnee.add(current);
            current = current.plusDays(1);
        }
        boolean toutDisponible = disponibilites.containsAll(periodeSelectionnee);
        if (toutDisponible) {
            disponibilites.removeAll(periodeSelectionnee);
            System.out.println("Période retirée : " + start + " -> " + end);
        } else {
            disponibilites.addAll(periodeSelectionnee);
            System.out.println("Période ajoutée : " + start + " -> " + end);
        }
        updateCalendarDisplay();
    }

    private void updateSelectionVisuals() {
        updateCalendarDisplay();
        if (selectionStart == null || selectionEnd == null) return;
        LocalDate start = selectionStart.isBefore(selectionEnd) ? selectionStart : selectionEnd;
        LocalDate end = selectionStart.isAfter(selectionEnd) ? selectionStart : selectionEnd;
        LocalDate current = start;
        while (!current.isAfter(end)) {
            tempSelection.add(current);
            current = current.plusDays(1);
        }
        for (Map.Entry<Label, LocalDate> entry : labelDateMap.entrySet()) {
            LocalDate date = entry.getValue();
            if (tempSelection.contains(date)) {
                Label l = entry.getKey();
                LocalDate today = LocalDate.now();
                boolean isToday = date.equals(today);
                boolean isDisponible = disponibilites.contains(date);
                if (isToday) {
                    l.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 5; -fx-background-color: #7272ff; -fx-font-size: 16px");
                } else {
                    l.setStyle("-fx-padding: 0; -fx-background-color: #18191b; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 16px");
                }
                selectedLabels.add(l);
            }
        }
    }

    private void updateCalendarDisplay() {
        for (Map.Entry<Label, LocalDate> entry : labelDateMap.entrySet()) {
            Label label = entry.getKey();
            LocalDate date = entry.getValue();
            applyDayStyle(label, date);
        }
    }

    private void clearTempSelection() {
        tempSelection.clear();
        selectedLabels.clear();
        selectionStart = null;
        selectionEnd = null;
        updateCalendarDisplay();
    }
}
