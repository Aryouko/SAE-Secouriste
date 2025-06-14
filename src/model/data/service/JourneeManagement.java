package model.data.service;

import model.dao.JourneeDAO;
import model.data.persistence.Journee;

import java.util.List;

public class JourneeManagement {
    private final JourneeDAO journeeDAO =  new JourneeDAO();

    public long getJourneeByJour(int jour, int mois, int annee) {
        return this.journeeDAO.findIdByJour(jour, mois, annee);
    }

    public void addJournee(Journee journee) {
        this.journeeDAO.insert(journee);
    }
}
