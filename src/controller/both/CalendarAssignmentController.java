package controller.both;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import model.data.persistence.DPS;

import java.time.LocalDate;
import java.util.List;

public class CalendarAssignmentController {

    @FXML
    private Pane calendarPane;

    private List<DPS> dpsList ;

    @FXML
    private GridPane gridPaneWeek;

    @FXML
    public void initialize() {
        setGridPaneWeek();
        drawLines();
    }

    private void setGridPaneWeek() {
        String[] daysOfWeek = new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        LocalDate today = LocalDate.now();

        int dayWeek = today.getDayOfWeek().getValue();
        this.gridPaneWeek.setAlignment(Pos.CENTER);
        this.gridPaneWeek.setHgap(10);
        this.gridPaneWeek.setVgap(10);
        this.gridPaneWeek.setStyle("-fx-padding: 25 75 25 25;");

        LocalDate startDate = today.minusDays(dayWeek - 1);
        for (int i = 0; i < 7; i++) {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);

            LocalDate day = startDate.plusDays(i);

            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setAlignment(Pos.CENTER);

            Label dayOfmonthLabel = new Label(String.valueOf(day.getDayOfMonth()));
            dayOfmonthLabel.setMaxWidth(Double.MAX_VALUE);
            dayOfmonthLabel.setAlignment(Pos.CENTER);

            if (day.equals(today)) {
                gridPane.setStyle("-fx-background-color: #121315; -fx-background-radius: 15");
                dayLabel.setStyle("-fx-font-size: 25; -fx-text-alignment: center; -fx-padding: 10; -fx-text-fill: #FFFFFF;");
                dayOfmonthLabel.setStyle("-fx-font-size: 35; -fx-font-weight: bold; -fx-text-align: center; -fx-text-fill: #FFFFFF;");
            } else {
                gridPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 15");
                dayLabel.setStyle("-fx-font-size: 25; -fx-text-alignment: center; -fx-padding: 10; -fx-text-fill: #000000;");
                dayOfmonthLabel.setStyle("-fx-font-size: 35; -fx-font-weight: bold; -fx-text-align: center; -fx-text-fill: #000000;");
            }

            gridPane.add(dayLabel, 0, 0);
            gridPane.add(dayOfmonthLabel, 0, 1);
            this.gridPaneWeek.add(gridPane, i + 1, 0);
        }
    }

    private void drawLines() {
        this.calendarPane.setPrefSize(2400, 980);
        this.calendarPane.setMinSize(2400, 980);
        this.calendarPane.getChildren().clear();
        double width = 2400;
        double height = 980;

        for (int h = 0; h <= 23; h++) {
            double y = h * (height / 24) + 50;
            Label hour = new Label(h + "h");
            hour.setLayoutX(width*0.03);
            hour.setLayoutY(y - (height*0.01));

            Line line = new Line(width * 0.05, y, width * 0.95, y);
            line.setStyle("-fx-stroke: #DDE0E2; -fx-stroke-width: 2;");

            calendarPane.getChildren().add(hour);
            calendarPane.getChildren().add(line);
        }
    }

    /**
     * Show event for
     */
    private void drawDPS() {
        calendarPane.getChildren();
        double width = calendarPane.getWidth();
        double height = calendarPane.getHeight();

        for (int h = 0; h <= 23; h++) {
            double y = h * (height / 24) + (height / 24);

        }


    }
}