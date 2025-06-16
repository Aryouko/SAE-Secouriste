package model.data.service;
import model.dao.DisponibiliteDAO;
import model.data.persistence.Disponibilite;
import model.data.persistence.Journee;
import model.data.persistence.Secouriste;

import java.util.ArrayList;

public class DisponibiliteManagement {

    private final DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();

    public ArrayList<Disponibilite> getDisponibilites(Secouriste secouriste) {
        return this.disponibiliteDAO.findById(secouriste);
    }

    public void removeDisponibilite(Secouriste secouriste, Journee journee) {
        this.disponibiliteDAO.deleteDisponibilite(secouriste.getIdSecouriste(), new JourneeManagement().getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee()));
    }
}
