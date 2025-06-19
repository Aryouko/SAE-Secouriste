package model.graph.utils;

import model.data.persistence.Necessite;
import model.dao.NecessiteDAO;
import model.data.service.CompetenceManagement;

import java.util.List;

public class TestMA {

    public static void main(String[] args) {
        System.out.println("TestMA class is ready to be implemented.");
        // This class is a placeholder for testing matrix-related functionalities.
        // You can implement test cases here to validate matrix operations.

        MatrixUtils matrixUtils = new MatrixUtils();
        NecessiteDAO necessiteDao = new NecessiteDAO();

        System.out.println("Vous pouvez ajouter vos tests pour les opérations sur les matrices ici.");

        List<Necessite> necessites = necessiteDao.findAll();

        CompetenceManagement competenceManagement = new CompetenceManagement();
        List<String> competence = competenceManagement.getCompetences();

        // Test de la méthode createSkillDependencyMap
        var skillDepMap = matrixUtils.createSkillDependencyMap(competence ,necessites);
        System.out.println("Résultat de createSkillDependencyMap :");
        for (var entry : skillDepMap.entrySet()) {
            System.out.println("Compétence " + entry.getKey() + " dépend de : " + entry.getValue());
        }
    }


}
