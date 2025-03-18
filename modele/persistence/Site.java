package modele.persistence;

public class Site {
    String code;
    String nom;
    float longitude;
    float latitude;

    /**
     * Getter of Code
     * @return the code of the site
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Getter of Nom
     * @return the name of the site
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Getter of Longitude
     * @return the longitude of the site
     */
    public float getLongitude() {
        return this.longitude;
    }

    /**
     * Getter of Latitude
     * @return the latitude of the site
     */
    public float getLatitude() {
        return this.latitude;
        //ok
    }
}
