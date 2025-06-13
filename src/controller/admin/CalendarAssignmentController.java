package controller.admin;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import model.data.persistence.DPS;

import java.util.List;

public class CalendarAssignmentController {

    @FXML
    private Pane calendarPane;

    private List<DPS> dpsList ;

    @FXML
    public void initialize() {
        calendarPane.widthProperty().addListener((obs, oldWidth, newWidth) -> drawLines());
        calendarPane.heightProperty().addListener((obs, oldHeight, newHeight) -> drawLines());
    }

    private void drawLines() {
        calendarPane.getChildren().clear();
        double width = calendarPane.getWidth();
        double height = calendarPane.getHeight();

        for (int h = 0; h <= 23; h++) {
            double y = h * (height / 24) + (height / 24);
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