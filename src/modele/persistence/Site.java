package src.modele.persistence;

/**
 * Site class
 */
public class Site {

    /**
     * Informations du site : code, nom et coordonnées géographiques.
     */
    String code;       // Code du site
    String nom;        // Nom du site
    float longitude;   // Longitude du site
    float latitude;    // Latitude du site


    /**
     * Getter of Code
     * @return the code of the site
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Setter of Code
     * @param code the code of the site to set
     */
    public void setCode(String code) {
        this.code = code;
    }



    /**
     * Getter of Nom
     * @return the name of the site
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Setter of Nom
     * @param nom the name of the site to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }




    /**
     * Getter of Longitude
     * @return the longitude of the site
     */
    public float getLongitude() {
        return this.longitude;
    }

    
    /**
     * Setter of Longitude
     * @param longitude the longitude of the site to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }




    /**
     * Getter of Latitude
     * @return the latitude of the site
     */
    public float getLatitude() {
        return this.latitude;
    }

    /**
     * Setter of Latitude
     * @param latitude the latitude of the site to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }








}
