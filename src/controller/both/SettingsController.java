package controller.both;

import javafx.fxml.FXML;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;

public class SettingsController {

    private static final Logger LOGGER = Logger.getLogger(SettingsController.class.getName());

    @FXML
    private TextField pseudoField;

    private String pseudo;

    private String bio;

    @FXML
    private TextField bioField;

    @FXML
    private ImageView pdp;

    @FXML
    private ImageView pdpProfil;

    /*
    @FXML
    private ImageView certif1;

    @FXML
    private ImageView certif2;

    @FXML
    private ImageView certif3;

    @FXML
    private ImageView certif4;

    @FXML
    private ImageView certif5;

    @FXML
    private ImageView certif6;
    */

    private String pseudoInitial;
    private String bioInitiale;
    private Image pdpInitiale;
    /*
    private Image certif1Initiale;
    private Image certif2Initiale;
    private Image certif3Initiale;
    private Image certif4Initiale;
    private Image certif5Initiale;
    private Image certif6Initiale;
    */

    public void initialize() {
        pseudo = "JeanDeLaFontaine";
        pseudoField.setText(pseudo);

        bio = "Ma biographie";
        bioField.setText(bio);

        pdp.setImage(new Image("/images/pdpChat.png"));
        pdpProfil.setImage(pdp.getImage());

        pseudoInitial = pseudo;
        bioInitiale = bio;
        pdpInitiale = pdp.getImage();
    }

    @FXML
    public void setPseudo() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Bloquer si plus de 15 caractères ou s'il contient un espace ou un tab
            if (newText.length() > 15 || newText.matches(".*[\\s\\t].*")) {
                return null;
            }
            return change;
        });
        pseudo = formatter.valueProperty().get();
        pseudoField.setTextFormatter(formatter);
        System.out.println("Nouveau pseudo : " + pseudo);
    }

    @FXML
    public void setBio() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Bloquer si plus de 250 caractères
            if (newText.length() > 250) {
                return null;
            }
            return change;
        });
        bio = formatter.valueProperty().get();
        bioField.setTextFormatter(formatter);
        System.out.println("Nouvelle bio : " + bio);
    }

    @FXML
    private void annulerClicked() {
        pseudoField.setText(pseudoInitial);
        bioField.setText(bioInitiale);
        pdp.setImage(pdpInitiale);
        pdpProfil.setImage(pdpInitiale);
        clipCircle(pdp);
        clipCircle(pdpProfil);
    }

    @FXML
    private void importerPdp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image PNG");

        // Filtrer les fichiers pour ne prendre que PNG
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers PNG (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        // Ouvrir la fenêtre de dialogue
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());

                pdp.setImage(image);
                pdpProfil.setImage(image);

                // Crop rond
                clipCircle(pdp);
                clipCircle(pdpProfil);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Erreur lors du chargement de l'image", e);
            }
        }
    }

    private void clipCircle(ImageView imageView) {
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        // Si fitWidth ou fitHeight ne sont pas définis, on peut récupérer la taille de l'image
        if (width <= 0) width = imageView.getImage().getWidth();
        if (height <= 0) height = imageView.getImage().getHeight();

        double radius = Math.min(width, height) / 2;

        Circle clip = new Circle(width / 2, height / 2, radius);
        imageView.setClip(clip);
    }
}