package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import model.data.persistence.DPS;
import model.data.service.AffectationManagement;
import model.data.service.BesoinManagement;
import model.data.service.DPSManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionEvenementController {

    @FXML
    private TilePane evenementTile;

    @FXML
    private ComboBox<String> sitesComboBox;

    @FXML
    private ComboBox<String> sportsComboBox;

    @FXML
    private AnchorPane gestionEvenement;

    private ArrayList<String> sports;

    private ArrayList<String> sites;

    private List<DPS> dpsList;

    private FenetreGestionController fenetreGestionController;

    private EvenementController evenementController;

    private final DPSManagement dpsManagement = new DPSManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private final BesoinManagement besoinManagement = new BesoinManagement();

    @FXML
    public void initialize() {

        this.sports = new ArrayList<>();
        this.sites = new ArrayList<>();
        this.dpsList = new ArrayList<>();

        this.evenementTile.setHgap(50);
        this.evenementTile.setVgap(50);
        this.evenementTile.setStyle("-fx-padding: 50;");
        this.evenementTile.setMaxWidth(1350);

        this.dpsList = this.dpsManagement.getDps();

        tileInitialize(this.dpsList);
        comboBoxInitialize();
    }

    private void tileInitialize(List<DPS> listDPS) {

        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (DPS dps : listDPS) {
            if (!this.sports.contains(dps.getSport().getNom())) {
                this.sports.add(dps.getSport().getNom());
            }

            if (!this.sites.contains(dps.getSite().getNom())) {
                this.sites.add(dps.getSite().getNom());
            }

            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(369, 376);
            gridPane.setMaxSize(369, 376);
            gridPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-border-color: #A0A0A0; -fx-border-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

            Label titre = new Label(dps.getName());
            titre.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");

            Label sport = new Label("Sport : " + dps.getSport().getNom());
            sport.setStyle("-fx-font-size: 15;");

            Label site = new Label("Lieu : " + dps.getSite().getNom());
            site.setStyle("-fx-font-size: 15;");

            GridPane subSubGridPane = new GridPane();
            subSubGridPane.setPrefSize(230, 22);
            subSubGridPane.setMaxSize(230, 22);

            Label jour = new Label(dps.getJournee().getJour() + " " + months[dps.getJournee().getMois() - 1] + " : ");
            Label dep = new Label(dps.getHoraireDepart() + "h");
            Label entre = new Label("au");
            Label fin = new Label(dps.getHoraireFin() + "h");
            jour.setMinWidth(115);
            dep.setMinWidth(75);
            entre.setMinWidth(50);
            fin.setMinWidth(75);
            jour.setAlignment(Pos.CENTER);
            dep.setAlignment(Pos.CENTER);
            entre.setAlignment(Pos.CENTER);
            fin.setAlignment(Pos.CENTER);
            jour.setStyle("-fx-font-size: 15;");
            dep.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 6; -fx-font-size: 15;");
            entre.setStyle("-fx-font-size: 15;");
            fin.setStyle("-fx-border-radius: 20; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 6; -fx-font-size: 15;");

            subSubGridPane.add(jour, 0, 0);
            subSubGridPane.add(dep, 1, 0);
            subSubGridPane.add(entre, 2, 0);
            subSubGridPane.add(fin, 3, 0);

            GridPane subGridPane1 = new GridPane();
            subGridPane1.setPrefSize(354, 305);
            subGridPane1.setMaxSize(354, 305);
            subGridPane1.setStyle("-fx-background-color: #B9D9FF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");

            HBox subSubHbox = new HBox();
            subSubHbox.setAlignment(Pos.CENTER);
            subSubHbox.setPrefWidth(354);

            Label membresAttr = new Label("Affectés : " + this.affectationManagement.getIdRescuersByDps(dps.getId()).size());
            membresAttr.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
            membresAttr.setAlignment(Pos.CENTER);
            membresAttr.setMaxWidth(Double.MAX_VALUE);

            Label membresNonAttr = new Label("Non affectés : " + this.besoinManagement.getBesoinByDPS(dps).getCompetences().size());
            membresNonAttr.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
            membresNonAttr.setAlignment(Pos.CENTER);
            membresNonAttr.setMaxWidth(Double.MAX_VALUE);

            HBox.setHgrow(membresAttr, Priority.ALWAYS);
            HBox.setHgrow(membresNonAttr, Priority.ALWAYS);

            subSubHbox.getChildren().addAll(membresAttr, membresNonAttr);

            Region spacer1 = new Region();
            spacer1.setMinHeight(10);

            Region spacer2 = new Region();
            spacer2.setMinHeight(5);

            Region spacer3 = new Region();
            spacer3.setMinHeight(15);

            Region spacer4 = new Region();
            spacer4.setMinHeight(65);

            subGridPane1.add(titre, 0, 0);
            subGridPane1.add(spacer1, 0, 1);
            subGridPane1.add(sport, 0, 2);
            subGridPane1.add(spacer2, 0, 3);
            subGridPane1.add(site, 0, 4);
            subGridPane1.add(spacer3, 0, 5);
            subGridPane1.add(subSubGridPane, 0, 6);
            subGridPane1.add(spacer4, 0, 7);
            subGridPane1.add(subSubHbox, 0, 8);

            GridPane subGridPane2 = new GridPane();
            subGridPane2.setPrefSize(354, 71);
            subGridPane2.setMaxSize(354, 71);

            Button majButton = new Button("Maintenance");
            majButton.setMinWidth(125);
            majButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-font-size: 15; -fx-border-color: #000000; -fx-border-width: 2px; -fx-text-fill: #000000;");
            Button supprButton = new Button("Suppression");
            supprButton.setMinWidth(125);
            supprButton.setStyle("-fx-background-color: #FF004D; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10; -fx-font-size: 15; -fx-border-color: #000000; -fx-border-width: 2px; -fx-text-fill: #FFFFFF;");

            majButton.setOnAction(event -> majAffect(dps));
            supprButton.setOnAction(event -> supprDps(dps));

            GridPane buttonGridPane = new GridPane();
            buttonGridPane.setAlignment(Pos.CENTER_RIGHT);
            buttonGridPane.setHgap(10);
            buttonGridPane.add(supprButton, 1, 0);

            if (!this.besoinManagement.getBesoinByDPS(dps).getCompetences().isEmpty()) {
                buttonGridPane.add(majButton, 0, 0);
            }

            subGridPane2.add(buttonGridPane, 0, 0);
            subGridPane2.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setMargin(buttonGridPane, new Insets(10, 0, 0, 0));

            gridPane.add(subGridPane1, 0, 0);
            gridPane.add(subGridPane2, 0, 1);

            this.evenementTile.getChildren().add(gridPane);
        }

        for (int i = 0; i < (3 - listDPS.size() % 3); i++) {
            GridPane emptyPane = new GridPane();
            emptyPane.setPrefSize(369, 367);
            emptyPane.setMaxSize(369, 376);
            emptyPane.setStyle("-fx-background-color: #EBF0F6; -fx-background-radius: 20; -fx-border-radius: 20; -fx-padding: 10;");
            this.evenementTile.getChildren().add(emptyPane);
        }
    }

    public void comboBoxInitialize() {
        this.sitesComboBox.getItems().add("Site");
        this.sportsComboBox.getItems().add("Sport");
        this.sitesComboBox.getSelectionModel().select("Site");
        this.sportsComboBox.getSelectionModel().select("Sport");
        this.sitesComboBox.getItems().addAll(this.sites);
        this.sportsComboBox.getItems().addAll(this.sports);
    }

    @FXML
    public void filtreUpdate() {
        this.evenementTile.getChildren().clear();
        ArrayList<DPS> listDPS = new ArrayList<>();
        for (DPS dps : this.dpsManagement.getDps()) {
            if ((dps.getSite().getNom().equals(this.sitesComboBox.getSelectionModel().getSelectedItem())
                    || this.sitesComboBox.getSelectionModel().getSelectedItem().equals("Site"))
                    && (dps.getSport().getNom().equals(this.sportsComboBox.getSelectionModel().getSelectedItem())
                    || this.sportsComboBox.getSelectionModel().getSelectedItem().equals("Sport"))) {
                listDPS.add(dps);
            }
        }
        tileInitialize(listDPS);
    }

    public void ajouterDpsList(DPS dps) {
        if (!this.sports.contains(dps.getSport().getNom())) {
            this.sports.add(dps.getSport().getNom());
            this.sportsComboBox.getItems().add(dps.getSport().getNom());
        }

        if (!this.sites.contains(dps.getSite().getNom())) {
            this.sites.add(dps.getSite().getNom());
            this.sitesComboBox.getItems().add(dps.getSite().getNom());
        }
    }

    public void retirerDpsList(DPS dps) {
        boolean autresSportExistent = false;
        for (DPS autreDps : this.dpsManagement.getDps()) {
            if (!(autreDps.getId() == dps.getId()) && autreDps.getSport().getNom().equals(dps.getSport().getNom())) {
                autresSportExistent = true;
            }
        }

        if (!autresSportExistent) {
            if (dps.getSport().getNom().equals(this.sportsComboBox.getSelectionModel().getSelectedItem())) {
                this.sportsComboBox.getSelectionModel().select("Sport");
            }
            this.sports.remove(dps.getSport().getNom());
            this.sportsComboBox.getItems().remove(dps.getSport().getNom());
        }

        boolean autresSiteExistent = false;
        for (DPS autreDps : this.dpsManagement.getDps()) {
            if (!(autreDps.getId() == dps.getId()) && autreDps.getSite().getNom().equals(dps.getSite().getNom())) {
                autresSiteExistent = true;
            }
        }

        if (!autresSiteExistent) {
            if (dps.getSite().getNom().equals(this.sitesComboBox.getSelectionModel().getSelectedItem())) {
                this.sitesComboBox.getSelectionModel().select("Site");
            }
            this.sites.remove(dps.getSite().getNom());
            this.sitesComboBox.getItems().remove(dps.getSite().getNom());
        }
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    public void setEvenementController(EvenementController evenementController) {
        this.evenementController = evenementController;
    }

    @FXML
    public void GestionSecouristeButton() {
        this.fenetreGestionController.loadContent2("/fxml/GestionSecouriste.fxml");
    }

    @FXML
    public void CreationDPSButton() {
        this.fenetreGestionController.fenetreCreationDPS();
    }

    private void majAffect(DPS dps) {
        try {
            this.affectationManagement.launchAffectation(dps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!this.besoinManagement.getBesoinByDPS(dps).getCompetences().isEmpty()) {
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
            alert.showAndWait();
        }
        filtreUpdate();
    }

    private void supprDps(DPS dps) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FenetreSupprDPS.fxml"));
            Parent overlayContent = loader.load();

            FenetreSupprDPSController controller = loader.getController();
            controller.initializeDPS(dps, this, this.evenementController);

            StackPane overlayPane = new StackPane();
            overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // fond semi-transparent sombre

            overlayPane.getChildren().add(overlayContent);
            StackPane.setAlignment(overlayContent, Pos.CENTER);

            AnchorPane.setTopAnchor(overlayPane, 0.0);
            AnchorPane.setBottomAnchor(overlayPane, 0.0);
            AnchorPane.setLeftAnchor(overlayPane, 0.0);
            AnchorPane.setRightAnchor(overlayPane, 0.0);

            this.gestionEvenement.getChildren().add(overlayPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
