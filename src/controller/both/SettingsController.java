package controller.both;

import controller.UtilsController;
import controller.admin.FenetreGestionController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.dao.DisponibiliteDAO;
import model.dao.PossessionDAO;
import model.data.persistence.Competence;
import model.data.persistence.Possession;
import model.data.persistence.Secouriste;
import model.data.service.PossessionManagement;
import model.data.service.SecouristeManagement;
import model.data.service.DisponibiliteManagement;

import java.io.*;
import java.util.*;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class SettingsController {

    private FenetreGestionController fenetreGestionController;
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private final DisponibiliteManagement disponibiliteManagement = new DisponibiliteManagement();
    Secouriste sec;
    private final Map<CheckBox, List<CheckBox>> dependances = new HashMap<>();
    private final Map<CheckBox, List<CheckBox>> reverseDependances = new HashMap<>();

    @FXML
    private AnchorPane settingsPane;
    @FXML
    private AnchorPane notifPane;
    @FXML
    private Label jourLabel;
    @FXML
    private Text prenomNomProfile;
    @FXML
    private Text prenomNomParam;
    @FXML
    private Circle pdpProfileCircle;
    @FXML
    private Circle pdpParamCircle;
    @FXML
    private CheckBox checkPSE1;
    @FXML
    private CheckBox checkPSE2;
    @FXML
    private CheckBox checkCE;
    @FXML
    private CheckBox checkCP;
    @FXML
    private CheckBox checkCO;
    @FXML
    private CheckBox checkSSA;
    @FXML
    private CheckBox checkVPSP;
    @FXML
    private CheckBox checkPBC;
    @FXML
    private CheckBox checkPBF;
    private byte[] nouvellePhoto;
    private boolean affichageNotif = false;

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

        } catch (Exception e) {
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

        initialize();
    }

    @FXML
    private void annulerClicked() {
        initialize();
    }

    @FXML
    private void notifClicked() {
        if (!affichageNotif) {
            UtilsController.linkToPage(notifPane, "/fxml/commonwindow/NotificationResized.fxml");
            affichageNotif = true;
        } else {
            UtilsController.linkToPage(settingsPane, "/fxml/commonwindow/Settings.fxml");
            affichageNotif = false;
        }
    }

    @FXML
    private void calendrierClicked() {
        UtilsController.linkToPage(settingsPane, "/fxml/layoutmanager/FenetreGestion.fxml");
    }

    @FXML
    private void fermerClicked() {
        UtilsController.linkToPage(settingsPane, "/fxml/layoutmanager/FenetreGestion.fxml");
    }

    @FXML
    private void supprimerClicked() {
        Secouriste sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
        UtilsController.linkToPage(settingsPane, "/fxml/auth/Connection.fxml");
        PossessionDAO possessionDAO = new PossessionDAO();
        DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();
        long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
        possessionDAO.deleteAllPossessionsForSecouriste(idSec);
        disponibiliteDAO.deleteAllDisponibilites(idSec);
        secouristeManagement.removeSecouriste(sec);
    }

    @FXML
    private void deconnecterClicked() {
        UtilsController.linkToPage(settingsPane, "/fxml/auth/Connection.fxml");
    }
}

