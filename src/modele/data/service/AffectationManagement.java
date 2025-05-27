package modele.data.service;
import modele.data.persistence.DPS;
import modele.data.persistence.Secouriste;
import modele.data.persistence.Competence;

public class AffectationManagement {
    private long idSecouriste;
    private long idDPS;
    private String competence;

    public AffectationManagement(long secouriste,long dps, String competence) {
        this.idSecouriste = secouriste;
        this.idDPS = dps;
        this.competence = competence;
    }

    /*
     * Getter to get idSecouriste
     * @return the id of rescuer
     */
    public long getIdSecouriste() {
        return idSecouriste;
    }

    /*
     * Getter to get idDPS
     * @return the id of DPS
     */
    public long getIdDPS() {
        return idDPS;
    }

    /*
     * Getter to get competence
     * @return the competence selected of rescuer
     */
    public String getCompetence() {
        return competence;
    }
}
