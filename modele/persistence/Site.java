package modele.persistence;

public class Site {
    String code;
    String nom;
    float longitude;
    float latitude;

    public String getCode() {
        return this.code;
    }

    public String getNom() {
        return this.nom;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }
}
