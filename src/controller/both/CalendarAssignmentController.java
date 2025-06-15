package controller.both;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.service.AffectationManagement;
import model.data.service.AuthentificationManagement;
import model.data.service.DPSManagement;
import model.data.service.SecouristeManagement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class CalendarAssignmentController {

    @FXML
    private Pane calendarPane;

    private List<DPS> dpsList ;

    @FXML
    private GridPane gridPaneWeek;

    private final AffectationManagement affectationManagement =  new AffectationManagement();

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    @FXML
    private Label dateLabel;

    @FXML
    private Label weekLabel;

    private LocalDate date;

    @FXML
    public void initialize() {
        dpsList = new ArrayList<>();
        for (Affectation affectation : affectationManagement.getAffectationsByRescuer(secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser()))) {
            dpsList.add(affectation.getDPSAffect());
        }
        this.date = LocalDate.now();
        setGridPaneWeek();
        drawLines();
        drawDPS();
    }

    private void setGridPaneWeek() {
        String[] daysOfWeek = new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        String[] months = new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};

        this.dateLabel.setText(months[this.date.plusDays(7 - this.date.getDayOfWeek().getValue()).getMonthValue() - 1] + " " + this.date.plusDays(7 - this.date.getDayOfWeek().getValue()).getYear());

        int dayWeek = this.date.getDayOfWeek().getValue();
        this.gridPaneWeek.setAlignment(Pos.CENTER);
        this.gridPaneWeek.setHgap(10);
        this.gridPaneWeek.setVgap(10);
        this.gridPaneWeek.setStyle("-fx-padding: 25 75 25 25;");

        LocalDate startDate = this.date.minusDays(dayWeek - 1);
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

            if (day.equals(LocalDate.now())) {
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
        calendarPane.setPrefWidth(1425);
        calendarPane.setPrefHeight(1600);
        calendarPane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.calendarPane.getChildren().clear();
        double width = 1425;
        double height = 1600;

        for (int h = 0; h <= 23; h++) {
            double y = h * (height / 24) + 50;
            Label hour = new Label(h + "h");
            hour.setLayoutX(width*0.03);
            hour.setLayoutY(y - (height*0.01));
            hour.setMinWidth(75);
            hour.setAlignment(Pos.CENTER);
            hour.setStyle("-fx-font-size: 16; -fx-text-alignment: center;");

            Line line = new Line(width * 0.11, y, width * 0.98, y);
            line.setStyle("-fx-stroke: #DDE0E2; -fx-stroke-width: 2;");

            calendarPane.getChildren().add(hour);
            calendarPane.getChildren().add(line);
        }
    }

    /**
     * Show event for
     */
    private void drawDPS() {
        double width = 1425;
        double height = 1600;
        double columnWidth = (width * 0.89) / 7; // ajusté pour laisser un peu de marge
        double hourHeight = height / 24.0;

        for (DPS dps : dpsList) {
            int dayWeek =this.date.getDayOfWeek().getValue();

            String[] styles = new  String[]{"-fx-background-color: #62AAFF; -fx-background-radius: 10;", "-fx-background-color: #FF4747; -fx-background-radius: 10;", "-fx-background-color: #58F58F; -fx-background-radius: 10;", "-fx-background-color: #FFC145; -fx-background-radius: 10;", "-fx-background-color: #FF9E36; -fx-background-radius: 10;", "-fx-background-color: #D336FF; -fx-background-radius: 10;", "-fx-background-color: #BB9368; -fx-background-radius: 10;"};

            LocalDate dpsDate = LocalDate.of(dps.getJournee().getAnnee(), dps.getJournee().getMois(), dps.getJournee().getJour());
            LocalDate startDate = this.date.minusDays(dayWeek - 1);
            for (int i = 0; i < 7; i++) {
                LocalDate currentDate = startDate.plusDays(i);
                if (dpsDate.equals(currentDate)) {

                    int startHour = dps.getHoraireDepart();
                    int endHour = dps.getHoraireFin();

                    double x = width * 0.06 + (i + 1) * (columnWidth - 13) - 58;
                    double y = 50 + startHour * hourHeight;
                    double heightDPS = (endHour - startHour) * hourHeight;

                    StackPane stackPane = new StackPane();
                    stackPane.setLayoutX(x);
                    stackPane.setLayoutY(y);
                    stackPane.setPrefWidth(columnWidth - 25);
                    stackPane.setPrefHeight(heightDPS); // petit espace visuel


                    stackPane.setStyle(styles[i]);

                    Label titleLabel = new Label(dps.getName());
                    titleLabel.setWrapText(true);
                    titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-text-alignment: center; -fx-font-size: 20");

                    Label locationLabel = new Label("Lieu : " + dps.getSite().getNom());
                    locationLabel.setWrapText(true);
                    locationLabel.setStyle("-fx-font-size: 16; -fx-text-alignment: center; -fx-text-fill: #FFFFFF");

                    Label sportLabel = new Label("Sport : " + dps.getSport().getNom());
                    sportLabel.setWrapText(true);
                    sportLabel.setStyle("-fx-font-size: 16; -fx-text-alignment: center; -fx-text-fill: #FFFFFF");

                    Label competenceLabel = new Label();
                    competenceLabel.setWrapText(true);
                    competenceLabel.setStyle("-fx-font-size: 16; -fx-text-alignment: center; -fx-text-fill: #FFFFFF");
                    for (Affectation affectation : affectationManagement.getAffectationsByDps(dps)) {
                        if (affectation.getSecouristeAffect().getIdSecouriste() == secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser()).getIdSecouriste()) {
                            competenceLabel.setText("Ma compétence attribué : " + affectation.getCompetenceAffect().getIntitule());
                        }
                    }

                    VBox vbox = new VBox(5); // 5px d’espacement vertical
                    vbox.setAlignment(Pos.CENTER);

                    vbox.getChildren().addAll(titleLabel, locationLabel, sportLabel, competenceLabel);

                    stackPane.getChildren().add(vbox);
                    calendarPane.getChildren().add(stackPane);
                }
            }
        }
    }

    @FXML
    public void nextWeek() {
        this.date = this.date.plusDays(7);
        if (LocalDate.now().equals(this.date)) {
            weekLabel.setText("Cette semaine");
        } else if (LocalDate.now() == this.date.minusDays(7)) {
            weekLabel.setText("Semaine prochaine");
        } else if (LocalDate.now() == this.date.plusDays(7)) {
            weekLabel.setText("Semaine précédente");
        } else {
            weekLabel.setText("Autre semaine");
        }
        this.calendarPane.getChildren().clear();
        setGridPaneWeek();
        drawLines();
        drawDPS();
    }

    @FXML
    public void previousWeek() {
        this.date = this.date.minusDays(7);
        if (LocalDate.now().equals(this.date)) {
            weekLabel.setText("Cette semaine");
        } else if (LocalDate.now().equals(this.date.minusDays(7))) {
            weekLabel.setText("Semaine prochaine");
        } else if (LocalDate.now().equals(this.date.plusDays(7))) {
            weekLabel.setText("Semaine précédente");
        } else {
            weekLabel.setText("Autre semaine");
        }
        this.calendarPane.getChildren().clear();
        setGridPaneWeek();
        drawLines();
        drawDPS();
    }
}