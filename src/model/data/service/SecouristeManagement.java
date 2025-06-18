package model.data.service;

import javafx.scene.image.Image;
import model.dao.DAOFactory;
import model.dao.SecouristeDAO;
import model.data.persistence.Secouriste;
import model.data.persistence.User;

import java.io.File;
import java.util.List;

public class SecouristeManagement {

    private static final SecouristeManagement instance = new SecouristeManagement();

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

    public boolean updatePhoto(long idSecouriste, byte[] image) {
        return secouristeDAO.insererPhoto(idSecouriste, image);
    }

    public Image recupererPhoto(long idSecouriste) {
        return secouristeDAO.recupererPhoto(idSecouriste);
    }

    public void removeSecouriste(Secouriste secouriste) {
        this.secouristeDAO.delete(secouriste);
    }

    public User getUserBySecouriste(Secouriste secouriste) {
        return this.secouristeDAO.findUserBySecouriste(secouriste);
    }

    public static SecouristeManagement getInstanceSecouristeManagement() {
        return instance;
    }
}
