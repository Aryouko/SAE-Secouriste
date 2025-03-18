package modele.persistence;

public class Affectation {
    private long idDPS;
    private long idSecouriste;
    private String intituleComp;
    private Secouriste secouristeAffect;
    private DPS DPSAffect;
    private Competence comptetenceAffect;


    public long getIdDPS() {
        return this.idDPS;
    }

    public void setIdDPS(long idDPS) {
        this.idDPS = idDPS;
    }

    public long getIdSecouriste() {
        return this.idSecouriste;
    }

    public void setIdSecouriste(long idSecouriste) {
        this.idSecouriste = idSecouriste;
    }

    public String getIntituleComp() {
        return this.intituleComp;
    }

    public void setIntituleComp(String intituleComp) {
        this.intituleComp = intituleComp;
    }

    public Secouriste getSecouristeAffect() {
        return this.secouristeAffect;
    }

    public void setSecouristeAffect(Secouriste secouristeAffect) {
        this.secouristeAffect = secouristeAffect;
    }

    public DPS getDPSAffect() {
        return this.DPSAffect;
    }

    public void setDPSAffect(DPS DPSAffect) {
        this.DPSAffect = DPSAffect;
    }

    public Competence getComptetenceAffect() {
        return this.comptetenceAffect;
    }

    public void setComptetenceAffect(Competence comptetenceAffect) {
        this.comptetenceAffect = comptetenceAffect;
    }
}
