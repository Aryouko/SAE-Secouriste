package controller.both;

import controller.UtilsController;
import controller.admin.FenetreGestionController;
import controller.admin.FenetreGestionInjectable;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.dao.AdministrateurDAO;
import model.dao.DisponibiliteDAO;
import model.dao.PossessionDAO;
import model.data.persistence.Administrateur;
import model.data.persistence.Competence;
import model.data.persistence.Possession;
import model.data.persistence.Secouriste;
import model.data.service.AdministrateurManagement;
import model.data.service.PossessionManagement;
import model.data.service.SecouristeManagement;

import java.io.*;
import java.util.*;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;

public class SettingsController implements FenetreGestionInjectable, MenuParalleleInjectable {

    private FenetreGestionController fenetreGestionController;

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private final AdministrateurManagement administrateurManagement = new AdministrateurManagement();
    private final AdministrateurDAO administrateurDAO = new AdministrateurDAO();
    Secouriste sec;
    Administrateur admin;
    private final Map<CheckBox, List<CheckBox>> dependances = new HashMap<>();
    private final Map<CheckBox, List<CheckBox>> reverseDependances = new HashMap<>();
    private MenuParalleleController menuParalleleController;

    @FXML private AnchorPane settingsPane;
    @FXML private Text prenomNomParam;
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
    @FXML private Line line;
    @FXML private Text compText;
    @FXML private Text certifText;
    private byte[] nouvellePhoto;


    @FXML
    public void initialize() {
        try {
            sec = secouristeManagement.getSecouristeById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            admin = administrateurDAO.findById(getInstanceAuthentificationManagement().getCurrentUser().getIdUser());
            if(getInstanceAuthentificationManagement().isAdmin()){
                prenomNomParam.setText(admin.getPrenom() + " " + admin.getNom());
                if (admin.getPhoto() != null) {
                    Image image = new Image(new ByteArrayInputStream(admin.getPhoto()));
                    pdpParamCircle.setFill(new ImagePattern(image));
                }
                checkPSE1.setVisible(false);
                checkPSE2.setVisible(false);
                checkCE.setVisible(false);
                checkCP.setVisible(false);
                checkCO.setVisible(false);
                checkSSA.setVisible(false);
                checkVPSP.setVisible(false);
                checkPBC.setVisible(false);
                checkPBF.setVisible(false);
                line.setVisible(false);
                compText.setVisible(false);
                certifText.setVisible(false);
            } else {
                prenomNomParam.setText(sec.getPrenom() + " " + sec.getNom());
                if (sec.getPhoto() != null) {
                    Image image = new Image(new ByteArrayInputStream(sec.getPhoto()));
                    pdpParamCircle.setFill(new ImagePattern(image));
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
            }
        } catch (Exception e) {

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
        if(getInstanceAuthentificationManagement().isAdmin()){
            long idAdmin = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
            if (nouvellePhoto != null) {
                boolean success = administrateurManagement.updatePhoto(idAdmin, nouvellePhoto);
                System.out.println("Nouvelle photo enregistrée en base.");
            } else {
                System.out.println("Aucune nouvelle photo à enregistrer.");
            }
            menuParalleleController.setRow(0, "/fxml/common/DisplayProfil.fxml");
            initialize();
        } else {
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
            PossessionDAO possessionDAO = new PossessionDAO();
            possessionDAO.deleteAllPossessionsForSecouriste(idSec);
            possessionDAO.insert(possession);
            menuParalleleController.setRow(0, "/fxml/common/DisplayProfil.fxml");
            initialize();
        }
    }

    @FXML
    private void annulerClicked() {
        initialize();
    }


    @FXML
    private void fermerClicked() {

        if (fenetreGestionController != null) {
            fenetreGestionController.loadContent2("/fxml/common/DashboardCalendarAssignment.fxml");
        }

        if (menuParalleleController != null) {
            menuParalleleController.linkToSettings();
        } else {
            System.out.println("menuParalleleController est null !");
        }
    }

    @FXML
    private void supprimerClicked() {
        if(getInstanceAuthentificationManagement().isAdmin()){
            UtilsController.linkToPage(fenetreGestionController.getFenetreGestion(), "/fxml/auth/Login.fxml");
            long idAdmin = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
            administrateurManagement.removeAdministrateur(admin);
        } else {
            UtilsController.linkToPage(fenetreGestionController.getFenetreGestion(), "/fxml/auth/Login.fxml");
            PossessionDAO possessionDAO = new PossessionDAO();
            DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();
            long idSec = getInstanceAuthentificationManagement().getCurrentUser().getIdUser();
            possessionDAO.deleteAllPossessionsForSecouriste(idSec);
            disponibiliteDAO.deleteAllDisponibilites(idSec);
            secouristeManagement.removeSecouriste(sec);
        }
    }

    @FXML
    private void deconnecterClicked() {
        UtilsController.linkToPage(fenetreGestionController.getFenetreGestion(), "/fxml/auth/Login.fxml");
    }

    public void setFenetreGestionController(FenetreGestionController fenetreGestionController) {
        this.fenetreGestionController = fenetreGestionController;
    }

    public void setMenuParalleleController(MenuParalleleController menuParalleleController) {
        this.menuParalleleController = menuParalleleController;
    }
}
