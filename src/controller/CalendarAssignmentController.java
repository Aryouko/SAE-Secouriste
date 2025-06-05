package controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class CalendarAssignmentController {

    @FXML
    private Pane calendarPane;

    @FXML
    public void initialize() {
        calendarPane.widthProperty().addListener((obs, oldWidth, newWidth) -> drawLines());
        calendarPane.heightProperty().addListener((obs, oldHeight, newHeight) -> drawLines());
    }

    private void drawLines() {
        calendarPane.getChildren().clear();
        double width = calendarPane.getWidth();
        double height = calendarPane.getHeight();
        for (int h = 8; h <= 20; h++) {
            double y = (h - 8) * (height / (20 - 8));
            Line line = new Line(width * 0.05, y, width * 0.95, y);
            line.setStyle("-fx-stroke: #DDE0E2; -fx-stroke-width: 2;");
            calendarPane.getChildren().add(line);
        }
    }
}