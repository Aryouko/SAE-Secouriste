package modele.persistence;

public class Sport {
    String code;
    String nom;

    /**
     * Getter of Code
     * @return the code of the sport
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Getter of Nom
     * @return the name of the sport
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Setter of Code
     * @param code the code of the sport to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Setter of Nom
     * @param nom the name of the sport to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}
