package modele.persistence;

/**
 * This class represents a possession.
 */
public class Possession {

    /**
     * The possession's identifier.
     */
    public Journee journeeDisp; // journeeDisp
    private Secouriste secouristeDisp; // secouristeDisp

    /**
     * Getter for the possession's identifier.
     * @return The possession's identifier.
     */
    public Journee getJourneeDisp() {
        return journeeDisp;
    }

    /**
     * Setter for the possession's identifier.
     * @param journeeDisp The possession's identifier.
     */
    public void setJourneeDisp(Journee journeeDisp) {
        this.journeeDisp = journeeDisp;
    }

    /**
     * Getter for the possession's identifier.
     * @return The possession's identifier.
     */
    public Secouriste getSecouristeDisp() {
        return secouristeDisp;
    }

    /**
     * Setter for the possession's identifier.
     * @param secouristeDisp The possession's identifier.
     */
    public void setSecouristeDisp(Secouriste secouristeDisp) {
        this.secouristeDisp = secouristeDisp;
    }
}
