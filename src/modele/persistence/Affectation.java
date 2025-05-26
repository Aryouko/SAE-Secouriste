package src.modele.persistence;
/**
 * This class link class Secouriste, DPS and Competence.
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
*/
public class Affectation {

    /**
     * Private variable containing the rescuer
     */
    private Secouriste secouristeAffect;

    /**
     * Private variable containing the DPS
     */
    private DPS DPSAffect;

    /**
     * Private variable containing the skill
     */
    private Competence competenceAffect;


    /**
     * Get the affectation rescuer
     * @return the instance of Secouriste
     */
    public Secouriste getSecouristeAffect() {
        return this.secouristeAffect;
    }

    /**
     * Set the affectation rescuer
     * @param secouristeAffect
     */
    public void setSecouristeAffect(Secouriste secouristeAffect) {
        this.secouristeAffect = secouristeAffect;
    }

    /**
     * Get the affectation DPS
     * @return the instance of DPS
     */
    public DPS getDPSAffect() {
        return this.DPSAffect;
    }

    /**
     * Set the affectation DPS
     * @param DPSAffect
     */
    public void setDPSAffect(DPS DPSAffect) {
        this.DPSAffect = DPSAffect;
    }

    /**
     * Get the affectation competence
     * @return the instance of Competence
     */
    public Competence getCompetenceAffect() {
        return this.competenceAffect;
    }

    /**
     * Set the affectation competence
     * @param competenceAffect
     */
    public void setComptetenceAffect(Competence competenceAffect) {
        this.competenceAffect = competenceAffect;
    }
}
