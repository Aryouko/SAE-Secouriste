package model.data.service;

import model.dao.JourneeDAO;
import model.data.persistence.Journee;

import java.util.List;

public class JourneeManagement {
    private final JourneeDAO journeeDAO =  new JourneeDAO();

    public long getJourneeByJour(int jour, int mois, int annee) {
        long id = this.journeeDAO.findIdByJour(jour, mois, annee);
        if (id == -1) {
            addJournee(new Journee(jour, mois, annee));
            id = getJourneeByJour(jour, mois, annee);
        }
        return id;
    }

    public void addJournee(Journee journee) {
        this.journeeDAO.insert(journee);
    }
}
