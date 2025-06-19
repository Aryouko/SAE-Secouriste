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

    /**
     * Met à jour un besoin dans la base de données en supprimant les anciennes compétences
     * et en insérant les nouvelles.
     *
     * @param besoin Le besoin à mettre à jour
     */
    public void updateBesoin(Besoin besoin) {
        if (besoin == null) {
            return;
        }

        // Supprimer l'ancien besoin
        this.besoinDAO.deleteByDps(besoin.getDps().getId());

        // S'il reste des compétences à pourvoir, on réinsère le besoin
        if (!besoin.getCompetences().isEmpty()) {
            this.besoinDAO.insert(besoin);
        }
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
