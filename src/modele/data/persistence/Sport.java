package modele.data.persistence;

/**
 * Sport class
 */
public class Sport {

    /**
     * Informations du sport : code et nom.
     */
    String code; // Code du sport
    String nom;  // Nom du sport

    /**
     * Constructor of Sport
     * @param code - code of Sport
     * @param nom - name of the sport
     */
    public Sport(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    /**
     * Getter of Code
     * @return the code of the sport
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Setter of Code
     * @param code the code of the sport to set
     */
    public void setCode(String code) {
        this.code = code;
    }



    /**
     * Getter of Nom
     * @return the name of the sport
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Setter of Nom
     * @param nom the name of the sport to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }





}
