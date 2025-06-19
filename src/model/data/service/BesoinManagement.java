package model.data.service;

import model.dao.BesoinDAO;
import model.data.persistence.Besoin;
import model.data.persistence.Competence;
import model.data.persistence.DPS;

/**
 * This class manages the Besoin (need) for DPS (Dossier de Prise en charge).
 * It provides methods to add, retrieve, and delete Besoin instances.
 *
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class BesoinManagement {

    /**
     * DAO instance for Besoin operations.
     */
    private final BesoinDAO besoinDAO = new BesoinDAO();

    /**
     * Adds a new Besoin to the database.
     * @param besoin the Besoin instance to be added
     */
    public void addBesoin(Besoin besoin) {
        this.besoinDAO.insert(besoin);
    }

    /**
     * Retrieves a Besoin by its associated DPS.
     * @param dps the DPS instance for which the Besoin is to be retrieved
     * @return the Besoin instance associated with the given DPS
     */
    public Besoin getBesoinByDPS(DPS dps) {
        return this.besoinDAO.findByDPS(dps);
    }

    /**
     * Retrieves the ID of a Besoin by its associated DPS and Competence.
     * @param dps the DPS instance for which the Besoin ID is to be retrieved
     * @param competence the Competence instance associated with the Besoin
     * @return the ID of the Besoin associated with the given DPS and Competence
     */
    public long getIdBesoinByDPSAndCompetence(DPS dps, Competence competence) {
        return this.besoinDAO.findByDPSAndCompetence(dps, competence);
    }

    /**
     * Deletes a Besoin by its associated DPS and Competence.
     * @param dps the DPS instance for which the Besoin is to be deleted
     * @param competence the Competence instance associated with the Besoin to be deleted
     */
    public void deleteBesoinByDPSAndCompetence(DPS dps, Competence competence) {
        this.besoinDAO.deleteByDPSAndCompetence(dps, competence);
    }

    /**
     * Deletes all Besoin instances associated with a specific DPS.
     * @param dps the DPS instance for which all associated Besoin instances are to be deleted
     */
    public void removeByDps(DPS dps) {
        besoinDAO.deleteByDps(dps.getId());
    }

    /**
     * Deletes a specific Besoin instance.
     * @param besoin the Besoin instance to be deleted
     */
    public void removeBesoin(Besoin besoin) {
        this.besoinDAO.deleteBesoin(besoin);
    }
}
