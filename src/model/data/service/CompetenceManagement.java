package model.data.service;

import model.dao.CompetenceDAO;
import model.data.persistence.Competence;

import java.util.ArrayList;

public class CompetenceManagement {
    private final CompetenceDAO competenceDAO = new CompetenceDAO();

    public ArrayList<String> getCompetences() {
        return this.competenceDAO.findAllIntitule();
    }
}
