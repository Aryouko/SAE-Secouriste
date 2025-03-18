package src;

public class Possession {
    private long idSecouriste;
    private String intituleComp;
    private Secouriste secouriste;
    private Competence competence;

    public long getIdSecouriste(){
        return this.idSecouriste;
    }

    public String getIntituleComp(){
        return this.intituleComp;
    }

    public Secouriste getSecouriste(){
        return this.secouriste;
    }

    public Competence getCompetence(){
        return this.competence;
    }
}
