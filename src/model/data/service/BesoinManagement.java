package model.data.service;

import model.dao.BesoinDAO;
import model.data.persistence.Besoin;
import model.data.persistence.Competence;
import model.data.persistence.DPS;

public class BesoinManagement {
    private final BesoinDAO besoinDAO = new BesoinDAO();

    public void addBesoin(Besoin besoin) {
        this.besoinDAO.insert(besoin);
    }

    public Besoin getBesoinByDPS(DPS dps) {
        return this.besoinDAO.findByDPS(dps);
    }

    public long getIdBesoinByDPSAndCompetence(DPS dps, Competence competence) {
        return this.besoinDAO.findByDPSAndCompetence(dps, competence);
    }

    public void deleteBesoinByDPSAndCompetence(DPS dps, Competence competence) {
        this.besoinDAO.deleteByDPSAndCompetence(dps, competence);
    }

    public void removeByDps(DPS dps) {
        besoinDAO.deleteByDps(dps.getId());
    }

    public void removeBesoin(Besoin besoin) {
        this.besoinDAO.deleteBesoin(besoin);
    }
}
