package controller.both;

import controller.UtilsController;
import controller.admin.FenetreGestionController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.dao.DisponibiliteDAO;
import model.dao.PossessionDAO;
import model.data.persistence.Competence;
import model.data.persistence.Possession;
import model.data.persistence.Secouriste;
import model.data.persistence.Disponibilite;
import model.data.persistence.Journee;
import model.data.service.PossessionManagement;
import model.data.service.SecouristeManagement;
import model.data.service.DisponibiliteManagement;
import model.data.service.JourneeManagement;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class SettingsController {

    private FenetreGestionController fenetreGestionController;
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();
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
    private boolean affichageNotif = false;
    private LocalDate selectionStart = null;
    private LocalDate selectionEnd = null;
    private final Map<Label, LocalDate> labelDateMap = new HashMap<>();
    private final List<Label> selectedLabels = new ArrayList<>();

    // Nouvelles variables pour gérer les disponibilités
    private final Set<LocalDate> disponibilites = new HashSet<>(); // Stockage permanent des disponibilités
    private final Set<LocalDate> tempSelection = new HashSet<>(); // Sélection temporaire en cours

    private ArrayList<Competence> getSelectedCompetences() {
        ArrayList<Competence> competences = new ArrayList<>();
        if (checkPSE1.isSelected()) competences.add(new Competence("PSE1"));
        if (checkPSE2.isSelected()) competences.add(new Competence("PSE2"));
        if (checkCE.isSelected()) competences.add(new Competence("CE"));
        if (checkCP.isSelected()) competences.add(new Competence("CP"));
        if (checkCO.isSelected()) competences.add(new Competence("CO"));
        if (checkSSA.isSelected()) competences.add(new Competence("SSA"));
        if (checkVPSP.isSelected()) competences.add(new Competence("VPSP"));
        if (checkPBC.isSelected()) competences.add(new Competence("PBC"));
        if (checkPBF.isSelected()) competences.add(new Competence("PBF"));
        return competences;
    }

    @FXML
    public void initialize() {
        try {
            sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            LocalDate today = LocalDate.now();
            this.mois = today.getMonthValue();
            this.annee = today.getYear();

            // Charger les disponibilités depuis la base de données
            chargerDisponibilites();

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
            }

            PossessionManagement possessionManagement = new PossessionManagement();
            Possession possession = possessionManagement.getPossessionBySecouriste(sec);

            checkPSE1.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "PSE1".equals(c.getIntitule())));
            checkPSE2.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "PSE2".equals(c.getIntitule())));
            checkCE.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "CE".equals(c.getIntitule())));
            checkCP.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "CP".equals(c.getIntitule())));
            checkCO.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "CO".equals(c.getIntitule())));
            checkSSA.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "SSA".equals(c.getIntitule())));
            checkPBC.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "PBC".equals(c.getIntitule())));
            checkPBF.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "PBF".equals(c.getIntitule())));
            checkVPSP.setSelected(possession.getCompetencesSec().stream().anyMatch(c -> "VPSP".equals(c.getIntitule())));

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

    private void chargerDisponibilites() {
        disponibilites.clear();
        ArrayList<Disponibilite> dispoList = disponibiliteManagement.getDisponibilites(sec);
        for (Disponibilite dispo : dispoList) {
            // La DAO retourne une Disponibilite avec une Journee construite depuis la requête JOIN
            // new Disponibilite(secouriste, new Journee(rs.getInt("Jour"), rs.getInt("Mois"), rs.getInt("Annee")))
            Journee journee = dispo.getJourDisp();
            LocalDate date = LocalDate.of(journee.getAnnee(), journee.getMois(), journee.getJour());
            disponibilites.add(date);
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
                // On ne décoche que si le prérequis qu'on vient de décocher est nécessaire
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

                // Met à jour l'image dans le FXML uniquement (pas en base)
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
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        }
    }

    @FXML
    private void enregistrerClicked() {
        long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
        if (nouvellePhoto != null) {
            boolean success = secouristeManagement.updatePhoto(idSec, nouvellePhoto);
            System.out.println("Nouvelle photo enregistrée en base.");
        } else {
            System.out.println("Aucune nouvelle photo à enregistrer.");
        }

        // Récupérer compétences sélectionnées
        ArrayList<Competence> competencesSelectionnees = getSelectedCompetences();
        Possession possession = new Possession(competencesSelectionnees, sec);

        // Appeler la DAO pour insérer les compétences dans la table Possession
        PossessionDAO possessionDAO = new PossessionDAO();

        possessionDAO.deleteAllPossessionsForSecouriste(idSec);
        possessionDAO.insert(possession);

        // Sauvegarder les disponibilités
        sauvegarderDisponibilites();

        initialize();
    }

    private void sauvegarderDisponibilites() {
        long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();

        // Récupérer les disponibilités actuelles en base
        ArrayList<Disponibilite> dispoCourantes = disponibiliteManagement.getDisponibilites(sec);
        Set<LocalDate> datesCourantes = new HashSet<>();

        for (Disponibilite dispo : dispoCourantes) {
            Journee journee = dispo.getJourDisp();
            LocalDate date = LocalDate.of(journee.getAnnee(), journee.getMois(), journee.getJour());
            datesCourantes.add(date);
        }

        // Supprimer les disponibilités qui ne sont plus sélectionnées
        for (LocalDate date : datesCourantes) {
            if (!disponibilites.contains(date)) {
                Journee journee = new Journee(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                disponibiliteManagement.removeDisponibilite(sec, journee);
            }
        }

        // Ajouter les nouvelles disponibilités
        for (LocalDate date : disponibilites) {
            if (!datesCourantes.contains(date)) {
                Journee journee = new Journee(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
                disponibiliteManagement.addDisponibilite(sec, journee);
            }
        }

        System.out.println("Disponibilités sauvegardées : " + disponibilites.size() + " jours");
    }

    @FXML
    private void annulerClicked(){
        initialize();
    }

    @FXML
    private void notifClicked() {
        if (!affichageNotif) {
            UtilsController.linkToPage(notifPane, "/fxml/both/NotificationResized.fxml");
            affichageNotif = true;
        } else {
            UtilsController.linkToPage(settingsPane, "/fxml/both/Settings.fxml");
            affichageNotif = false;
        }
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
        Secouriste sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
        UtilsController.linkToPage(settingsPane, "/fxml/both/Connexion.fxml");
        PossessionDAO possessionDAO = new PossessionDAO();
        DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();
        long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
        possessionDAO.deleteAllPossessionsForSecouriste(idSec);
        disponibiliteDAO.deleteAllDisponibilites(idSec);
        secouristeManagement.removeSecouriste(sec);
    }

    @FXML
    private void deconnecterClicked(){
        UtilsController.linkToPage(settingsPane, "/fxml/both/Connexion.fxml");
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

    private void populateCalendar(int annee, int mois) {
        calendarGrid.getChildren().clear();
        labelDateMap.clear();
        selectedLabels.clear();
        tempSelection.clear();

        // Ajout du titre du mois/année avec style
        String[] months = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };

        jourLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 24px");
        jourLabel.setText(months[mois - 1] + " " + annee);

        // Jours de la semaine, en commençant par lundi
        String[] days = { "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim" };

        // Ajout des noms des jours en première ligne (row 0)
        for (int i = 0; i < days.length; i++) {
            Label dayHeaderLabel = new Label(days[i]);
            dayHeaderLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px");
            calendarGrid.add(dayHeaderLabel, i, 0);
        }

        YearMonth yearMonth = YearMonth.of(annee, mois);
        LocalDate firstDay = yearMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // 1 = lundi, etc.

        // Calcul de la colonne de départ (0-based) pour le 1er jour
        int col = dayOfWeek - 1;
        int row = 1; // On commence à la ligne 1, sous les noms des jours

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(annee, mois, day);
            Label dayLabel = new Label(String.valueOf(day));

            // Déterminer le style selon l'état de la date
            applyDayStyle(dayLabel, date);

            dayLabel.setMinSize(40, 40);
            dayLabel.setMaxSize(40, 40);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setFocusTraversable(true);
            dayLabel.setPickOnBounds(true);

            // Map label to date
            labelDateMap.put(dayLabel, date);

            // Add mouse handlers
            setupMouseHandlers(dayLabel);

            calendarGrid.add(dayLabel, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

        // Remonter le jour actuel au-dessus des autres
        for (Node node : calendarGrid.getChildren()) {
            if (node instanceof Label label) {
                LocalDate date = labelDateMap.get(label);
                if (date != null && date.equals(LocalDate.now())) {
                    // Retirer et ré-ajouter pour remonter dans l'ordre d'affichage
                    calendarGrid.getChildren().remove(label);
                    calendarGrid.getChildren().add(label);
                    break;
                }
            }
        }

        // Gestion du glisser-déposer
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
            // Jour actuel ET disponible
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 5;-fx-background-insets: 1; -fx-background-color: #30308f; -fx-font-size: 16px");
        } else if (isToday) {
            // Jour actuel seulement
            dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 5;-fx-background-insets: 1; -fx-background-color: #4A4AE4; -fx-font-size: 16px");
        } else if (isDisponible) {
            // Jour disponible
            dayLabel.setStyle("-fx-padding: 0;-fx-background-insets: 1; -fx-background-color: #7272ff; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 16px");
        } else {
            // Jour normal
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

                // Traiter la sélection
                traiterSelection(start, end);

                // Réinitialiser la sélection temporaire
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

        // Vérifier si toute la période est déjà disponible
        boolean toutDisponible = disponibilites.containsAll(periodeSelectionnee);

        if (toutDisponible) {
            // Retirer toute la période
            disponibilites.removeAll(periodeSelectionnee);
            System.out.println("Période retirée : " + start + " -> " + end);
        } else {
            // Ajouter toute la période
            disponibilites.addAll(periodeSelectionnee);
            System.out.println("Période ajoutée : " + start + " -> " + end);
        }

        // Mettre à jour l'affichage
        updateCalendarDisplay();
    }

    private void updateSelectionVisuals() {
        // Réinitialiser l'affichage
        updateCalendarDisplay();

        if (selectionStart == null || selectionEnd == null) return;

        LocalDate start = selectionStart.isBefore(selectionEnd) ? selectionStart : selectionEnd;
        LocalDate end = selectionStart.isAfter(selectionEnd) ? selectionStart : selectionEnd;

        // Appliquer le style de sélection temporaire
        LocalDate current = start;
        while (!current.isAfter(end)) {
            tempSelection.add(current);
            current = current.plusDays(1);
        }

        // Mettre à jour l'affichage avec la sélection temporaire
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