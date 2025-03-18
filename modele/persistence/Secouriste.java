package modele.persistence;

public class Secouriste {
    private long id;
    private String nom;
    private String prenom;
    private String dateNaissace;
    private String email;
    private String tel;
    private String adresse;


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
        return this.dateNaissace;
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
     * Getter of Tel
     * 
     * @return the phone number of the secouriste
     */
    public String getTel(){
        return this.tel;
    }

    /**
     * Getter of Adresse
     * 
     * @return the address of the secouriste
     */
    public String getAdresse(){
        return this.adresse;
    }


}
