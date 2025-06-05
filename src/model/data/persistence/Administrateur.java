package model.data.persistence;

/**
 * Secouriste class
 */
public class Administrateur {
    /**
     * Informations personnelles du secouriste.
     */
    private long id;       // Identifiant unique
    private String nom;     // Nom du secouriste
    private String prenom;  // Prénom du secouriste
    private String dateNaissance; // Date de naissance (corrigé)
    private String email;   // Adresse email
    private String tel;     // Numéro de téléphone
    private String adresse; // Adresse postale

    /**
     * Constructor of Secouriste
     *
     * @param id the id of the secouriste
     * @param nom the name of the secouriste
     * @param prenom the first name of the secouriste
     * @param dateNaissance the birth date of the secouriste
     * @param email the email of the secouriste
     * @param tel the phone number of the secouriste
     * @param adresse the address of the secouriste
     */
    public Administrateur(long id, String nom, String prenom, String dateNaissance, String email, String tel, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
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
     * Getter of DateNaissace
     * 
     * @return the birth date of the secouriste
     */
    public String getDateNaissace(){
        return this.dateNaissance;
    }

    /**
     * Setter of DateNaissace
     * @param dateNaissance the birth date of the secouriste
     */
    public void setDateNaissace(String dateNaissance){
        this.dateNaissance = dateNaissance;
    }

    /**
     * Getter of Email
     * 
     * @return the email of the secouriste
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Setter of Email
     * 
     * @param email the email of the secouriste to set
     */
    public void setEmail(String email){
        this.email = email;
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

}
