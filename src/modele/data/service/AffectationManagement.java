package modele.data.service;
import modele.data.persistence.DPS;
import modele.data.persistence.Secouriste;
import modele.data.persistence.Competence;

public class AffectationManagement {    private long idSecouriste;
    private long idDPS;
    private String competence;

    /**
     * Constructor for AffectationManagement
     * @param secouriste the id of the rescuer
     * @param dps the id of the DPS
     * @param competence the competence selected of rescuer
     */
    public AffectationManagement(Secouriste secouriste,DPS dps, Competence competence) {
        this.idSecouriste = secouriste.getIdSecouriste();
        this.idDPS = dps.getId();
        this.competence = competence.getIntitule();
    }

    /**
     * Getter to get idSecouriste
     * @return the id of rescuer
     */
    public long getIdSecouriste() {
        return idSecouriste;
    }

    /**
     * Getter to get idDPS
     * @return the id of DPS
     */
    public long getIdDPS() {
        return idDPS;
    }

    /**
     * Getter to get competence
     * @return the competence selected of rescuer
     */
    public String getCompetence() {
        return competence;
    }
    }
