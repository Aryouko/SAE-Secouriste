package src.controller;
import java.time.LocalDate;
import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CalendarController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        populateCalendar(today.getYear(), today.getMonthValue());
    }

    public void populateCalendar(int year, int month) {
        calendarGrid.getChildren().clear();

        // Jours de la semaine, en commençant par lundi
        String[] days = { "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim" };

        // Ajout des noms des jours en première ligne (row 0)
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            calendarGrid.add(dayLabel, i, 0);
        }

        YearMonth yearMonth = YearMonth.of(year, month);

        // Le jour de la semaine du 1er du mois (1 = Lundi, ... 7 = Dimanche)
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();

        int daysInMonth = yearMonth.lengthOfMonth();

        // Calcul de la colonne de départ (0-based) pour le 1er jour
        int col = firstDayOfWeek - 1;
        int row = 1;  // On commence à la ligne 1, sous les noms des jours

        // Ajout des jours du mois dans la grille
        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-text-fill: white;");
            calendarGrid.add(dayLabel, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }
}

