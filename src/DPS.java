package src;

public class DPS {
    private long id;
    private int horaireDepart;
    private int horaireFin;
    private String siteDPS;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHoraireDepart() {
        return this.horaireDepart;
    }

    public void setHoraireDepart(int horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public int getHoraireFin() {
        return this.horaireFin;
    }

    public void setHoraireFin(int horaireFin) {
        this.horaireFin = horaireFin;
    }

    public String getSiteDPS() {
        return this.siteDPS;
    }

    public void setSiteDPS(String siteDPS) {
        this.siteDPS = siteDPS;
    }
}
