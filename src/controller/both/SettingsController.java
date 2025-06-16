package controller.both;

import controller.UtilsController;
import controller.admin.FenetreGestionController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.data.persistence.Secouriste;
import model.data.service.SecouristeManagement;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class SettingsController {

    private FenetreGestionController fenetreGestionController;
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    Secouriste sec;
    private final Map<CheckBox, List<CheckBox>> dependances = new HashMap<>();
    private final Map<CheckBox, List<CheckBox>> reverseDependances = new HashMap<>();

    @FXML private AnchorPane settingsPane;
    @FXML private AnchorPane notifPane;
    @FXML private GridPane calendarGrid;
    @FXML private Label jourLabel;
    private int mois;
    private int annee;
    @FXML private Text prenomNomProfile;
    @FXML private Text prenomNomParam;
    @FXML private Circle pdpProfileCircle;
    @FXML private Circle pdpParamCircle;
    @FXML private CheckBox checkPSE1;
    @FXML private CheckBox checkPSE2;
    @FXML private CheckBox checkCE;
    @FXML private CheckBox checkCP;
    @FXML private CheckBox checkCO;
    @FXML private CheckBox checkSSA;
    @FXML private CheckBox checkVPSP;
    @FXML private CheckBox checkPBC;
    @FXML private CheckBox checkPBF;
    private byte[] nouvellePhoto;

    @FXML
    public void initialize() {
        try {
            sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            LocalDate today = LocalDate.now();
            this.mois = today.getMonthValue();
            this.annee = today.getYear();
            populateCalendar(this.annee, mois);
            prenomNomProfile.setText(sec.getPrenom() + " " + sec.getNom());
            prenomNomParam.setText(sec.getPrenom() + " " + sec.getNom());
            if (sec.getPhoto() != null) {
                Image image = new Image(new ByteArrayInputStream(sec.getPhoto()));
                if (image.isError()) {
                    pdpProfileCircle.setFill(new ImagePattern(new Image("/images/anonyme.png", false)));
                    pdpParamCircle.setFill(new ImagePattern(new Image("/images/anonyme.png", false)));
                } else {
                    pdpProfileCircle.setFill(new ImagePattern(image));
                    pdpParamCircle.setFill(new ImagePattern(image));
                }
            } else {
                // Image par défaut si aucun byte n'est enregistré
                pdpProfileCircle.setFill(new ImagePattern(new Image("/images/anonyme.png", false)));
                pdpParamCircle.setFill(new ImagePattern(new Image("/images/anonyme.png", false)));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Définir les dépendances "vers le bas" (cocher les prérequis)
        dependances.put(checkCO, List.of(checkCP));
        dependances.put(checkCP, List.of(checkCE));
        dependances.put(checkCE, List.of(checkPSE2));
        dependances.put(checkPSE2, List.of(checkPSE1));
        dependances.put(checkSSA, List.of(checkPSE1));
        dependances.put(checkVPSP, List.of(checkPSE2));
        dependances.put(checkPBF, List.of(checkPBC));

        // Construire la map inverse "vers le haut"
        for (Map.Entry<CheckBox, List<CheckBox>> entry : dependances.entrySet()) {
            for (CheckBox prerequis : entry.getValue()) {
                reverseDependances.computeIfAbsent(prerequis, k -> new ArrayList<>()).add(entry.getKey());
            }
        }

        // Listener sur tous les checkboxes
        Set<CheckBox> allCheckboxes = new HashSet<>();
        allCheckboxes.addAll(dependances.keySet());
        dependances.values().forEach(allCheckboxes::addAll);

        for (CheckBox cb : allCheckboxes) {
            cb.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    cocherDependenciesRecursivement(cb);
                } else {
                    decocherSuperieursRecursivement(cb);
                }
            });
        }
    }

    private void cocherDependenciesRecursivement(CheckBox checkBox) {
        List<CheckBox> deps = dependances.get(checkBox);
        if (deps != null) {
            for (CheckBox dep : deps) {
                if (!dep.isSelected()) {
                    dep.setSelected(true);
                    cocherDependenciesRecursivement(dep); // appel récursif
                }
            }
        }
    }

    private void decocherSuperieursRecursivement(CheckBox checkBox) {
        List<CheckBox> superieurs = reverseDependances.get(checkBox);
        if (superieurs != null) {
            for (CheckBox sup : superieurs) {
                // On ne décoche que si le prérequis qu’on vient de décocher est nécessaire
                boolean doitDecocher = dependances.getOrDefault(sup, List.of())
                        .contains(checkBox) && !checkBox.isSelected();
                if (doitDecocher && sup.isSelected()) {
                    sup.setSelected(false);
                    decocherSuperieursRecursivement(sup); // appel récursif
                }
            }
        }
    }

    @FXML
    private void importerPdp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                Image image = new Image(fis);

                // Met à jour l’image dans le FXML uniquement (pas en base)
                pdpProfileCircle.setFill(new ImagePattern(image));
                pdpParamCircle.setFill(new ImagePattern(image));

                // Remettre le curseur au début pour relire le fichier
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    FileInputStream fis2 = new FileInputStream(selectedFile);
                    fis2.transferTo(baos);
                    nouvellePhoto = baos.toByteArray();
                }

                System.out.println("Photo chargée avec succès (non enregistrée en base).");

            } catch (IOException e) {
                System.out.println("Erreur lors du chargement de l’image : " + e.getMessage());
            }
        }
    }

    @FXML
    private void enregistrerClicked() {
        if (nouvellePhoto != null) {
            long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
            boolean success = secouristeManagement.updatePhoto(idSec, nouvellePhoto);
            sec.setPhoto(nouvellePhoto);
            initialize();
            System.out.println("Nouvelle photo enregistrée en base.");
        } else {
            System.out.println("Aucune nouvelle photo à enregistrer.");
        }
    }

    @FXML
    private void annulerClicked(){
        initialize();
    }

    @FXML
    private void notifClicked(){
        UtilsController.linkToPage(notifPane, "/fxml/both/NotificationResized.fxml");
    }

    @FXML
    private void calendrierClicked(){
        UtilsController.linkToPage(settingsPane, "/fxml/admin/FenetreGestion.fxml");
    }

    @FXML
    private void fermerClicked(){
        UtilsController.linkToPage(settingsPane, "/fxml/admin/FenetreGestion.fxml");
    }

    @FXML
    private void supprimerClicked(){

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

