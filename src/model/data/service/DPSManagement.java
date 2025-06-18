package model.data.service;

import java.util.ArrayList;
import java.util.List;

import model.dao.DPSDAO;
import model.data.persistence.DPS;
import model.data.persistence.Journee;
import model.data.persistence.Site;
import model.data.persistence.Sport;

public class DPSManagement {
    private final DPSDAO dpsDAO = new DPSDAO();

    public List<DPS> getDps(){
        return this.dpsDAO.findAll();
    }

    public void addDps(DPS dps) {
        this.dpsDAO.insert(dps);
    }

    public boolean exists(long id) {
        return (this.dpsDAO.findById(id) != null);
    }

    public DPS getDpsById(long id) {
        return this.dpsDAO.findById(id);
    }

    public DPS getDpsByName(String nom) {
        return this.dpsDAO.findByName(nom);
    }

    public long numberOfDps(){
        return this.dpsDAO.findAll().size();
    }

    public ArrayList<String> getDpsName() {
        return this.dpsDAO.findDPSName();
    }

    public void removeDps(DPS dps) {
        this.dpsDAO.deleteByIdDPS(dps.getId());
    }

    public ArrayList<DPS> getDpsByDay(long idDay) {
        return this.dpsDAO.findByDay(idDay);
    }

    public void updateDps(DPS dps) {
        this.dpsDAO.updateDps(dps);
    }
}
