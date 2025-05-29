package modele.data.persistence;
import modele.data.persistence.Secouriste;
import modele.data.persistence.Competence;
import java.util.ArrayList;

/**
 * This class represents a possession.
 */
public class Possession {

    /**
     * The possession's identifier.
     */
    public ArrayList<Competence> competencesSec ; // journeeDisp
    private Secouriste secouriste; // secouristeDisp

    /**
     * Constructor of Possession
     * @param competencesSec - list of competences
     * @param secouriste - a rescuer
     */
    public Possession(ArrayList<Competence> competencesSec, Secouriste secouriste) {
        this.competencesSec = competencesSec;
        this.secouriste = secouriste;
    }

    /**
     * Getter for the possession's identifier.
     * @return The possession's identifier.
     */
    public ArrayList<Competence> getCompetencesSec() {
        return this.competencesSec;
    }

    /**
     * Getter for the possession's identifier.
     * @return The possession's identifier.
     */
    public Secouriste getSecouriste() {
        return secouriste;
    }

    /**
     * Method allow to add competence
     * @param competence - new competence for the rescuer
     * @throws RuntimeException - if competence necessity is absent
     */
    public void addCompetence(Competence competence) throws RuntimeException {
        Competence compnecessite = competence.necessite();
        boolean verif = false;
        for (Competence comp : this.competencesSec) {
            if (comp.equals(compnecessite)) {
                verif = true;
            }
        }
        if (verif) {
            this.competencesSec.add(competence);
        } else {
            throw new RuntimeException("Competence necessite absente");
        }
    }
}
