package src.modele.persistence;

/**
 * DPS (Emergency Preparedness System)
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class DPS {

    /**
     * DPS id
     */
    private long id;

    /**
     * Start time
     */
    private int horaireDepart;

    /**
     * End time
     */
    private int horaireFin;

    /**
     * Place of the DPS
     */
    private Site site;

    /**
     * Sport of the DPS
     */
    private Sport sport;

    /**
     * Day of the DPS
     */
    private Journee journee;

    /**
     * Get the DPS id
     * @return the DPS id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Set the DPS id
     * @param id the DPS id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the start time
     * @return the start time
     */
    public int getHoraireDepart() {
        return this.horaireDepart;
    }

    /**
     * Set the start time
     * @param horaireDepart the start time
     */
    public void setHoraireDepart(int horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    /**
     * Get the end time
     * @return the end time
     */
    public int getHoraireFin() {
        return this.horaireFin;
    }

    /**
     * Set the end time
     * @param horaireFin the end time
     */
    public void setHoraireFin(int horaireFin) {
        this.horaireFin = horaireFin;
    }

    /**
     * Get the place of the DPS
     * @return the place of the DPS
     */
    public Site getSite() {
        return this.site;
    }

    /**
     * Set the place of the DPS
     * @param site the place of the DPS
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * Get the sport of the DPS
     * @return the sport of the DPS
     */
    public Sport getSport() {
        return this.sport;
    }

    /**
     * Set the sport of the DPS
     * @param sport the sport of the DPS
     */
    public void setSport(Sport sport) {
        this.sport = sport;
    }

    /**
     * Get the day of the DPS
     * @return the day of the DPS
     */
    public Journee getJournee() {
        return this.journee;
    }

    /**
     * Set the day of the DPS
     * @param journee the day of the DPS
     */ 
    public void setJournee(Journee journee) {
        this.journee = journee;
    }
}
