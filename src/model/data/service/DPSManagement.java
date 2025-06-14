package model.data.service;

import java.util.ArrayList;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.DPSDAO;
import model.data.persistence.DPS;

public class DPSManagement {
    private final DPSDAO dpsDAO = new DPSDAO();

    public List<DPS> getDps(){
        return this.dpsDAO.findAll();
    }

    public void addDps(DPS dps) {
        this.dpsDAO.insert(dps);
    }

    public boolean exists(long id) {
        return this.dpsDAO.findById(id);
    }

    public long numberOfDps(){
        return this.dpsDAO.findAll().size();
    }
}
