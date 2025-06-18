package model.data.service;

import javafx.scene.image.Image;
import model.dao.AdministrateurDAO;
import model.data.persistence.Administrateur;
import model.data.persistence.Secouriste;

import java.util.List;

public class AdministrateurManagement {
    private final AdministrateurDAO administrateurDAO = new AdministrateurDAO();

    public List<Administrateur> getAdministrateurs() {
        return this.administrateurDAO.findAll();
    }

    public Administrateur getAdministrateurById(long idAdministrateur) {
        return administrateurDAO.findById(idAdministrateur);
    }

    public boolean updatePhoto(long idSecouriste, byte[] image) {
        return administrateurDAO.insererPhoto(idSecouriste, image);
    }

    public Image recupererPhoto(long idSecouriste) {
        return administrateurDAO.recupererPhoto(idSecouriste);
    }

    public void addAdministrateur(Administrateur administrateur) {
        administrateurDAO.insert(administrateur);
    }

    public void removeAdministrateur(Administrateur administrateur) {
        this.administrateurDAO.delete(administrateur);
    }
}
