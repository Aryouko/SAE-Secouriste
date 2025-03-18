package modele.persistence;

public class Disponibilite {
    private long idSecouristeDisp;
    private int jourJour;
    private int moisJour;
    private int anneeJour;
    private Secouriste secouristeDisp;
    private Journee jourDisp;

    public long getIdSecouristeDisp() {
        return this.idSecouristeDisp;
    }

    public void setIdSecouristeDisp(long idSecouristeDisp) {
        this.idSecouristeDisp = idSecouristeDisp;
    }

    public int getJourJour() {
        return this.jourJour;
    }

    public void setJourJour(int jourJour) {
        this.jourJour = jourJour;
    }

    public int getMoisJour() {
        return this.moisJour;
    }

    public void setMoisJour(int moisJour) {
        this.moisJour = moisJour;
    }

    public int getAnneeJour() {
        return this.anneeJour;
    }

    public void setAnneeJour(int anneeJour) {
        this.anneeJour = anneeJour;
    }

    public Secouriste getSecouristeDisp() {
        return this.secouristeDisp;
    }

    public void setSecouristeDisp(Secouriste secouristeDisp) {
        this.secouristeDisp = secouristeDisp;
    }

    public Journee getJourDisp() {
        return this.jourDisp;
    }

    public void setJourDisp(Journee jourDisp) {
        this.jourDisp = jourDisp;
    }
}
