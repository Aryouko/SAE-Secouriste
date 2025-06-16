package model.data.persistence;

import java.util.Objects;

/**
 * This class stores name of competence which exists
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
*/
public class Competence {

    /**
     * Private variable containing the name of competence
     */
    private String intitule;

    public Competence(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Get the name of competence
     * @return the name of competence
     */
    public String getIntitule() {
        return this.intitule;
    }

    /**
     * Set the name of competence
     * @param intitule the new name of this competence
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competence that = (Competence) o;
        return Objects.equals(intitule, that.intitule); // ou id si tu as un identifiant unique
    }
}
