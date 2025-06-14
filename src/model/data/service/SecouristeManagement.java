package model.data.service;

import model.dao.SecouristeDAO;
import model.data.persistence.Secouriste;

import java.util.List;

public class SecouristeManagement {
    private final SecouristeDAO secouristeDAO = new SecouristeDAO();

    public List<Secouriste> getSecouristes() {
        return this.secouristeDAO.findAll();
    }

    public Secouriste getSecouristeById(long idSecouriste) {
        return secouristeDAO.findById(idSecouriste);
    }
}
