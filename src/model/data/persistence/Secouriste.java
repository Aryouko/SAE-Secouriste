package model.data.persistence;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Secouriste class
 */
public class Secouriste {
    /**
     * Informations personnelles du secouriste.
     */
    private long id;       // Identifiant unique
    private String nom;     // Nom du secouriste
    private String prenom;  // Prénom du secouriste
    private String dateNaissance; // Date de naissance (corrigé)
    private String tel;     // Numéro de téléphone
    private String adresse; // Adresse postale
    private byte[] photo;

    /**
     * Constructor of Secouriste
     *
     * @param id the id of the secouriste
     * @param nom the name of the secouriste
     * @param prenom the first name of the secouriste
     * @param dateNaissance the birth date of the secouriste
     * @param tel the phone number of the secouriste
     * @param adresse the address of the secouriste
     */
    public Secouriste(long id, String nom, String prenom, String dateNaissance, String tel, String adresse, byte[] photo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.adresse = adresse;
        if  (photo == null) {
            try {
                Path path = Paths.get("src/main/resources/images/anonyme.png");
                byte[] imageBytes = Files.readAllBytes(path);
                this.photo = imageBytes;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            this.photo = photo;
        }
    }

    /**
     * Getter of IdSecouriste
     * 
     * @return the id of the secouriste
     */
    public long getIdSecouriste(){
        return this.id;
    }

    /**
     * Getter of Nom
     * 
     * @return the name of the secouriste
     */
    public String getNom(){
        return this.nom;
    }


    /**
     * Getter of Prenom
     * 
     * @return the first name of the secouriste
     */
    public String getPrenom(){
        return this.prenom;
    }


    /**
     * Getter of DateNaissance
     * 
     * @return the birth date of the secouriste
     */
    public String getDateNaissance(){
        return this.dateNaissance;
    }

    /**
     * Setter of DateNaissance
     * @param dateNaissance the birth date of the secouriste
     */
    public void setDateNaissance(String dateNaissance){
        this.dateNaissance = dateNaissance;
    }


    /**
     * Getter of Tel
     *
     * @return the phone number of the secouriste
     */
    public String getTel(){
        return this.tel;
    }

    /**
     * Setter of Tel
     * 
     * @param tel the phone number of the secouriste to set
     */
    public void setTel(String tel){
        this.tel = tel;
    }

    /**
     * Getter of Adresse
     * 
     * @return the address of the secouriste
     */
    public String getAdresse(){
        return this.adresse;
    }

    /**
     * Setter of Adresse
     * 
     * @param adresse the address of the secouriste to set
     */
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Image getPhotoAsImage() {
        return new Image(new ByteArrayInputStream(photo));
    }
}
