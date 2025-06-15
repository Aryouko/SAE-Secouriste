package controller.both;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.data.persistence.Secouriste;
import model.data.service.SecouristeManagement;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class SettingsController {

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    Secouriste sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());

    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label jourLabel;
    private int mois;
    private int annee;
    @FXML
    private Text prenomNomProfile;
    @FXML
    private Text prenomNomParam;
    @FXML
    private ImageView pdpProfile;
    @FXML
    private ImageView pdpParam;

    @FXML
    public void initialize() {
        try {
            LocalDate today = LocalDate.now();
            this.mois = today.getMonthValue();
            this.annee = today.getYear();
            populateCalendar(this.annee, mois);
            prenomNomProfile.setText(sec.getPrenom() + " " + sec.getNom());
            prenomNomParam.setText(sec.getPrenom() + " " + sec.getNom());
            pdpProfile.setImage(new Image(new ByteArrayInputStream(sec.getPhoto())));
            pdpParam.setImage(new Image(new ByteArrayInputStream(sec.getPhoto())));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void importerPdp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            long idSecouriste = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();

            boolean success = secouristeManagement.insererPhoto(idSecouriste, selectedFile);

            if (success) {
                // Recharge le secouriste mis à jour depuis la BDD
                Secouriste secMisAJour = secouristeManagement.getSecouristeById(idSecouriste);
                if (secMisAJour != null && secMisAJour.getPhoto() != null) {
                    Image image = new Image(new ByteArrayInputStream(secMisAJour.getPhoto()));

                    // Met à jour l’image recadrée en rond
                    setCircularImage(pdpProfile, image);
                    setCircularImage(pdpParam, image);

                    System.out.println("Photo mise à jour avec succès.");
                }
            } else {
                System.out.println("Erreur lors de l'importation de la photo.");
            }
        }
    }

    private void setCircularImage(ImageView imageView, Image image) {
        imageView.setImage(image);

        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;

        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }


    @FXML
    private void moisSuivant() {
        LocalDate today = LocalDate.now();
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
        LocalDate today = LocalDate.now();
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

    public void populateCalendar(int year, int month) {

        String[] months = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre" };
        jourLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 24px");
        jourLabel.setText(months[this.mois - 1] + " " + this.annee);

        // Jours de la semaine, en commençant par lundi
        String[] days = { "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim" };

        // Ajout des noms des jours en première ligne (row 0)
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px");
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
            if (day == LocalDate.now().getDayOfMonth() && this.mois == LocalDate.now().getMonthValue() && this.annee == LocalDate.now().getYear()) {
                dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 100; -fx-background-color: #4A4AE4; -fx-font-size: 16px");
            } else {
                dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px");
            }
            dayLabel.setMinSize(40, 40);
            dayLabel.setMaxSize(40, 40);
            dayLabel.setAlignment(Pos.CENTER);
            calendarGrid.add(dayLabel, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }

}

