package model.data.service;

import model.dao.PossessionDAO;
import model.data.persistence.Competence;
import model.data.persistence.Possession;
import model.data.persistence.Secouriste;

public class PossessionManagement {
    private final PossessionDAO possessionDAO = new PossessionDAO();

    public Possession getPossessionBySecouriste(Secouriste secouriste) {
        return this.possessionDAO.find(secouriste);
    }

    public void addPossession(Possession possession) {
        this.possessionDAO.insert(possession);
    }

    public void removePossession(Secouriste secouriste, Competence competence) {
        this.possessionDAO.deletePossession(secouriste.getIdSecouriste(), competence.getIntitule());
    }
}
