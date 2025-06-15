package model.data.service;

import javafx.scene.image.Image;
import model.dao.DAOFactory;
import model.dao.SecouristeDAO;
import model.data.persistence.Secouriste;

import java.io.File;
import java.util.List;

public class SecouristeManagement {
    private final SecouristeDAO secouristeDAO = new SecouristeDAO();

    public List<Secouriste> getSecouristes() {
        return this.secouristeDAO.findAll();
    }

    public Secouriste getSecouristeById(long idSecouriste) {
        return secouristeDAO.findById(idSecouriste);
    }

    public List<Secouriste> findAll() {
        return secouristeDAO.findAll();
    }


    public List<Secouriste> findByIdJournee(long idJournee) {
        return secouristeDAO.findByDay(idJournee);
    }

    public boolean insererPhoto(long idSecouriste, File imageFile) {
        return secouristeDAO.insererPhoto(idSecouriste, imageFile);
    }

    public Image recupererPhoto(long idSecouriste) {
        return secouristeDAO.recupererPhoto(idSecouriste);
    }
}
