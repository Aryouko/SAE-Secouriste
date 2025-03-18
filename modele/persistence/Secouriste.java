package modele.persistence;

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
    private String email;   // Adresse email
    private String tel;     // Numéro de téléphone
    private String adresse; // Adresse postale



    /**
     * Getter of IdSecouriste
     * 
     * @return the id of the secouriste
     */
    public long getIdSecouriste(){
        return this.id;
    }

    /**
     * Setter of IdSecouriste
     * @return the id of the secouriste
     */
    public void setIdSecouriste(long id){
        this.id = id;
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
     * Setter of Nom
     * @return the name of the secouriste
     */
    public void setNom(String nom){
        this.nom = nom;
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
     * Setter of Prenom
     * @return the first name of the secouriste
     */
    public void setPrenom(String prenom){
        this.prenom = prenom;
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
     * @return the birth date of the secouriste
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
