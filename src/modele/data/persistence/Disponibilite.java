package modele.data.persistence;

/**
 * Availability of a secourist
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class Disponibilite {

    /**
     * Secourist available
     */
    private Secouriste secouristeDisp;

    /**
     * Day available
     */
    private Journee jourDisp;

    /**
     * Get the secourist available
     * @return the secourist available
     */
    public Secouriste getSecouristeDisp() {
        return this.secouristeDisp;
    }

    /**
     * Set the secourist available
     * @param secouristeDisp the secourist available
     */
    public void setSecouristeDisp(Secouriste secouristeDisp) {
        this.secouristeDisp = secouristeDisp;
    }

    /**
     * Get the day available
     * @return the day available
     */
    public Journee getJourDisp() {
        return this.jourDisp;
    }

    /**
     * Set the day available
     * @param jourDisp the day available
     */
    public void setJourDisp(Journee jourDisp) {
        this.jourDisp = jourDisp;
    }
}
