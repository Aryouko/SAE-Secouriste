package model.data.service;

import model.dao.SportDAO;
import model.data.persistence.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportManagement {
    private final SportDAO sportDAO = new SportDAO();

    public List<Sport> getSports(){
        return this.sportDAO.findAll();
    }

    public Sport getSport(int id){
        return this.sportDAO.findById(id);
    }

    public Sport getSportByName(String name){
        return this.sportDAO.findByName(name);
    }
}
