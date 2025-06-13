package model.data.service;

import java.util.ArrayList;

import model.dao.DAOFactory;
import model.data.persistence.DPS;

public class DPSManagement {
    private ArrayList<DPS> listDPS;

    public DPSManagement() {
        listDPS = new ArrayList<>();
    }

    public DPSManagement(ArrayList<DPS> listDPS) {
        this.listDPS = listDPS;
    }

    public ArrayList<DPS> getListDPS() {
        return DAOFactory.getDPSDAO().findAll();
    }

    public void addDPS(DPS dps) {
        listDPS.add(dps);
    }

    public void removeDPS(DPS dps) {
        listDPS.remove(dps);
    }
}
