package model.data.service;

import model.dao.AdministrateurDAO;
import model.data.persistence.Administrateur;

import java.util.List;

public class AdministrateurManagement {
    private final AdministrateurDAO administrateurDAO = new AdministrateurDAO();

    public List<Administrateur> getAdministrateurs() {
        return this.administrateurDAO.findAll();
    }

    public Administrateur getAdministrateurById(long idAdministrateur) {
        return administrateurDAO.findById(idAdministrateur);
    }
}
