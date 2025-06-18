package model.data.service;
import model.dao.AffectationDAO;
import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.persistence.Secouriste;
import model.graph.assignment.AssignmentGreedy;

import java.util.List;

/**
 * Class allow to collect all affectation
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class AffectationManagement {
    private final AffectationDAO affectationDAO = new AffectationDAO();

    public List<Integer> getIdRescuersByDps(long idDps) {
        return this.affectationDAO.findIdRescuerByDPS(idDps);
    }

    public boolean rescuerAvailable(long idDay, long idRescuer) {
        return !this.affectationDAO.rescuerThisDay(idDay, idRescuer);
    }

    public boolean isExist(Affectation affectation) {
        return this.affectationDAO.exists(affectation);
    }

    public void addAffectation(Affectation affectation) {
        this.affectationDAO.insert(affectation);
    }

    public List<Affectation> getAffectationsByRescuer(Secouriste secouriste) {
        return this.affectationDAO.findByRescuer(secouriste.getIdSecouriste());
    }

    public List<Affectation> getAffectationsByDps(DPS dps) {
        return this.affectationDAO.findByDPS(dps.getId());
    }

    public void launchAffectation(DPS dps) throws Exception {
        new AssignmentGreedy().AssignmentRescuersGreedy(dps);
    }

    public void removeAffectation(Affectation affectation) {
        this.affectationDAO.delete(affectation);
    }

}
