package model.data.service;

import model.dao.NecessiteDAO;
import model.data.persistence.Necessite;

import java.util.List;

public class NecessiteManagement {
    private final NecessiteDAO necessiteDAO =  new NecessiteDAO();

    public List<Necessite> getNecessites() {
        return this.necessiteDAO.findAll();
    }

    public void removeNecessite(Necessite necessite) {
        this.necessiteDAO.deleteNecessite(necessite);
    }

    public boolean isCreate(Necessite necessite) {
        return this.necessiteDAO.isCreate(necessite);
    }

    public void addNecessite(Necessite newNecessite) {
        this.necessiteDAO.insertNecessite(newNecessite);
    }
}
