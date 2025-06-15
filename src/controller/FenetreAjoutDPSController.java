package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import model.data.persistence.*;
import model.data.service.*;
import model.graphCelianTest.samedia17h30.graph.AssignmentGreedy;

import java.time.LocalDate;
import java.util.ArrayList;

public class FenetreAjoutDPSController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ComboBox<String> sportComboBox;

    @FXML
    private ComboBox<String> siteComboBox;

    @FXML
    private ComboBox<Integer> horaireDebComboBox;

    @FXML
    private ComboBox<Integer> horaireFinComboBox;

    @FXML
    private ComboBox<Integer> jourComboBox;

    @FXML
    private ComboBox<String> moisComboBox;

    @FXML
    private ComboBox<Integer> anneeComboBox;

    @FXML
    private ComboBox<Integer> comboBoxPSE1;

    @FXML
    private ComboBox<Integer> comboBoxPSE2;

    @FXML
    private ComboBox<Integer> comboBoxSSA;

    @FXML
    private ComboBox<Integer> comboBoxCE;

    @FXML
    private ComboBox<Integer> comboBoxVPSP;

    @FXML
    private ComboBox<Integer> comboBoxCP;

    @FXML
    private ComboBox<Integer> comboBoxPBC;

    @FXML
    private ComboBox<Integer> comboBoxCO;

    @FXML
    private ComboBox<Integer> comboBoxPBF;

    @FXML
    private TextField nomTextField;

    @FXML
    private Label infosLabel;

    LocalDate date;

    private int horaireDeb;

    private int horaireFin;

    private final String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    private Site site;

    private Sport sport;

    private GestionEvenementController gestionEvenementController;

    private EvenementController evenementController;

    private final SportManagement sportManagement = new SportManagement();

    private final SiteManagement siteManagement = new SiteManagement();

    private final DPSManagement dpsManagement = new DPSManagement();

    private final BesoinManagement besoinManagement = new BesoinManagement();

    private final AffectationManagement  affectationManagement = new AffectationManagement();

    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        this.date = today.plusDays(7);

        this.infosLabel.setWrapText(true);
        this.infosLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        this.horaireDeb = 8;
        this.horaireFin = 12;

        this.sport = this.sportManagement.getSports().get(0);
        this.site = this.siteManagement.getSites().get(0);

        initializeComboBoxCompetences();
        initializeComboBoxSport();
        initializeComboBoxSite();
        initializeComboBoxHoraire();
        initializeComboBoxDate();
    }

    private void initializeComboBoxCompetences() {
        this.comboBoxCE.getItems().clear();
        this.comboBoxPSE1.getItems().clear();
        this.comboBoxPSE2.getItems().clear();
        this.comboBoxSSA.getItems().clear();
        this.comboBoxVPSP.getItems().clear();
        this.comboBoxCP.getItems().clear();
        this.comboBoxPBC.getItems().clear();
        this.comboBoxCO.getItems().clear();
        this.comboBoxPBF.getItems().clear();

        for (int i = 0; i <= 50; i++) {
            this.comboBoxPSE1.getItems().add(i);
            this.comboBoxPSE2.getItems().add(i);
            this.comboBoxSSA.getItems().add(i);
            this.comboBoxCE.getItems().add(i);
            this.comboBoxVPSP.getItems().add(i);
            this.comboBoxCP.getItems().add(i);
            this.comboBoxPBC.getItems().add(i);
            this.comboBoxCO.getItems().add(i);
            this.comboBoxPBF.getItems().add(i);
        }
        this.comboBoxPSE1.getSelectionModel().select(0);
        this.comboBoxPSE2.getSelectionModel().select(0);
        this.comboBoxSSA.getSelectionModel().select(0);
        this.comboBoxCE.getSelectionModel().select(0);
        this.comboBoxVPSP.getSelectionModel().select(0);
        this.comboBoxCP.getSelectionModel().select(0);
        this.comboBoxPBC.getSelectionModel().select(0);
        this.comboBoxCO.getSelectionModel().select(0);
        this.comboBoxPBF.getSelectionModel().select(0);
    }

    private void initializeComboBoxSport() {
        this.sportComboBox.getItems().clear();

        for (Sport s : this.sportManagement.getSports()) {
            this.sportComboBox.getItems().add(s.getNom());
        }

        if (this.sport != null) {
            this.sportComboBox.getSelectionModel().select(this.sport.getNom());
        }
    }

    private void initializeComboBoxSite() {
        this.siteComboBox.getItems().clear();

        for (Site s : this.siteManagement.getSites()) {
            this.siteComboBox.getItems().add(s.getNom());
        }

        if (this.site != null) {
            this.siteComboBox.getSelectionModel().select(this.site.getNom());
        }
    }

    private void initializeComboBoxHoraire() {
        initializeComboBoxHoraireDeb();
        this.horaireDebComboBox.getSelectionModel().select(this.horaireDeb);
        initializeComboBoxHoraireFin();
        this.horaireFinComboBox.getSelectionModel().select(this.horaireFin);
    }

    private void initializeComboBoxHoraireDeb() {
        this.horaireDebComboBox.getItems().clear();

        for (int i = 0; i <= 24; i++) {
            this.horaireDebComboBox.getItems().add(i);
        }
    }

    private void initializeComboBoxHoraireFin() {
        this.horaireFinComboBox.getItems().clear();

        for (int i = 0; i <= 24; i++) {
            this.horaireFinComboBox.getItems().add(i);
        }
    }

    private void initializeComboBoxDate() {
        this.jourComboBox.getItems().clear();
        this.moisComboBox.getItems().clear();
        this.anneeComboBox.getItems().clear();

        LocalDate todayLimit = LocalDate.now().plusDays(7);

        for (int i = 1; i <= this.date.lengthOfMonth(); i++) {
            if (!(this.date.getMonthValue() == todayLimit.getMonthValue() && this.date.getYear() == todayLimit.getYear() && i < todayLimit.getDayOfMonth())) {
                this.jourComboBox.getItems().add(i);
            }
        }

        if (this.jourComboBox.getItems().contains(this.date.getDayOfMonth())) {
            this.jourComboBox.getSelectionModel().select((Integer) this.date.getDayOfMonth());
        }

        for (int i = 1; i <= 12; i++) {
            if (!(this.date.getYear() == todayLimit.getYear() && i < todayLimit.getMonthValue())) {
                this.moisComboBox.getItems().add(this.months[i - 1]);
            }
        }

        this.moisComboBox.getSelectionModel().select(this.months[this.date.getMonthValue() - 1]);

        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i <= currentYear + 2; i++) {
            this.anneeComboBox.getItems().add(i);
        }

        this.anneeComboBox.getSelectionModel().select((Integer) this.date.getYear());
    }

    @FXML
    public void filterUpdate(ActionEvent event) {
        Object source = event.getSource();

        if (source == this.sportComboBox) {
            String selectedSport = this.sportComboBox.getSelectionModel().getSelectedItem();
            if (selectedSport != null && !selectedSport.equals(this.sport.getNom())) {
                this.sport = this.sportManagement.getSportByName(selectedSport);
            }
        } else if (source == this.siteComboBox) {
            String selectedSite = this.siteComboBox.getSelectionModel().getSelectedItem();
            if (selectedSite != null && !selectedSite.equals(this.site.getNom())) {
                this.site = this.siteManagement.getSiteByName(selectedSite);
            }
        } else if (source == this.horaireDebComboBox) {
            Integer deb = this.horaireDebComboBox.getSelectionModel().getSelectedItem();
            if (deb != null) {
                this.horaireDeb = deb;
            }
        } else if (source == this.horaireFinComboBox) {
            Integer fin = this.horaireFinComboBox.getSelectionModel().getSelectedItem();
            if (fin != null) {
                this.horaireFin = fin;
            }
        } else if (source == this.jourComboBox || source == this.moisComboBox || source == this.anneeComboBox) {
            Integer jour = this.jourComboBox.getSelectionModel().getSelectedItem();
            String moisNom = this.moisComboBox.getSelectionModel().getSelectedItem();
            Integer annee = this.anneeComboBox.getSelectionModel().getSelectedItem();

            if (jour != null && moisNom != null && annee != null) {
                int mois = 0;

                for (int i = 0; i < this.months.length; i++) {
                    if (this.months[i].equals(moisNom)) {
                        mois = i + 1;
                        break;
                    }
                }

                LocalDate newDate = LocalDate.of(annee, mois, jour);

                if (!newDate.equals(this.date)) {
                    this.date = newDate;
                    initializeComboBoxDate();
                }
            }
        }
    }

    public DPS ajoutDPS() {
        DPS dps = null;
        if (this.horaireDeb >= horaireFin) {
            this.infosLabel.setText("Erreur : Horaire de début >= Horaire de fin");
        } else {
            long id = this.dpsManagement.numberOfDps();
            while (this.dpsManagement.exists(id)) {
                id++;
            }
            if (this.nomTextField.getText().isEmpty()) {
                this.infosLabel.setText("Erreur : Nom de DPS n'existe pas");
            } else {
                boolean dpsNameExists = false;
                for (DPS autreDps : dpsManagement.getDps()) {
                    if (autreDps.getName().equals(this.nomTextField.getText())) {
                    dpsNameExists = true;
                    }
                }
                if (!dpsNameExists) {
                    Journee journee = new Journee(this.date.getDayOfMonth(), this.date.getMonthValue(), this.date.getYear());
                    dps = new DPS(id, this.nomTextField.getText(), this.horaireDeb, this.horaireFin, this.site, this.sport, journee);
                } else {
                    this.infosLabel.setText("Erreur : Nom de DPS déjà existant");
                }
            }
        }
        return dps;
    }

    public void affectDPS() {
        DPS dps = this.ajoutDPS();
        if (dps != null) {
            ArrayList<Competence> competences = new ArrayList<>();

            for (int i = 0; i < this.comboBoxCE.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("CE"));
            }

            for (int i = 0; i < this.comboBoxCO.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("CO"));
            }

            for (int i = 0; i < this.comboBoxCP.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("CP"));
            }

            for (int i = 0; i < this.comboBoxPSE1.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("PSE1"));
            }

            for (int i = 0; i < this.comboBoxPSE2.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("PSE2"));
            }

            for (int i = 0; i < this.comboBoxPBC.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("PBC"));
            }

            for (int i = 0; i < this.comboBoxSSA.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("SSA"));
            }

            for (int i = 0; i < this.comboBoxPBF.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("PBF"));
            }

            for (int i = 0; i < this.comboBoxVPSP.getSelectionModel().getSelectedItem(); i++) {
                competences.add(new Competence("VPSP"));
            }

            if (competences.isEmpty()) {
                this.infosLabel.setText("Erreur : Aucune compétences renseignées");
            } else {
                this.dpsManagement.addDps(dps);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Affectation partielle");
                alert.setHeaderText("Certaines compétences n'ont pas été affectées");
                alert.setContentText("Il sera possible de mettre à jour l'affectation plus tard.");

                alert.initStyle(StageStyle.UNDECORATED);
                alert.setGraphic(null);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getScene().setFill(Color.TRANSPARENT);

                Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
                if (headerLabel != null) {
                    headerLabel.setStyle(
                            "-fx-font-size: 18px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-text-fill: #2C3E50; " +
                                    "-fx-padding: 20 0 10 0;"
                    );
                }

                Label contentLabel = (Label) dialogPane.lookup(".content");
                if (contentLabel != null) {
                    contentLabel.setStyle(
                            "-fx-font-size: 14px; " +
                                    "-fx-text-fill: #34495E; " +
                                    "-fx-padding: 10 20 20 20; " +
                                    "-fx-line-spacing: 2px;"
                    );
                }

                dialogPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 35; -fx-border-width: 3; border-radius: 35; -fx-border-color: #000000");

                dialogPane.getStylesheets().add("data:text/css,.dialog-pane {-fx-background-radius: 20; -fx-border-radius: 20; } " +
                        ".dialog-pane .header-panel {-fx-background-radius: 20 20 0 0; } " +
                        ".dialog-pane .button-bar {-fx-background-radius: 0 0 20 20; }");

                dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: #0C0D0F; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius : 15;");
                try {
                    this.besoinManagement.addBesoin(new Besoin(dps, competences));
                    this.affectationManagement.launchAffectation(dps);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (!this.besoinManagement.getBesoinByDPS(dps).getCompetences().isEmpty()) {
                    alert.showAndWait();
                }
                this.gestionEvenementController.ajouterDpsList(dps);
                this.gestionEvenementController.filtreUpdate();
                this.evenementController.initialize();
                annuleDPS();
            }
        }
    }

    public void annuleDPS() {
        // Le parent de rootPane est overlayPane (StackPane)
        Node overlayPane = rootPane.getParent();

        // Le parent de overlayPane est fenetreGestion (AnchorPane)
        if (overlayPane != null && overlayPane.getParent() instanceof AnchorPane) {
            AnchorPane fenetreGestion = (AnchorPane) overlayPane.getParent();
            fenetreGestion.getChildren().remove(overlayPane);
        }
    }

    public void initializeGestionEvenementController(GestionEvenementController gestionEvenementController) {
        this.gestionEvenementController = gestionEvenementController;
    }

    public void initializeEvenementController(EvenementController evenementController) {
        this.evenementController = evenementController;
    }
}