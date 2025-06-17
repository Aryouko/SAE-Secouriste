package model.graph.test;

import model.data.persistence.Necessite;
import model.dao.DAOFactory;
import model.graph.GraphAlgorithms;
import model.graph.MatrixUtils;

import java.util.List;
import java.util.Map;
/**
 * Test no methode de base pour verifier le bon fonctionnement
 */
public class BasicGraphFunctionTest {

    static MatrixUtils matrixUtils = new MatrixUtils();

    public static void main(String[] args) {


        // Récupère la liste des nécessités
        List<Necessite> skillNeededList = DAOFactory.getNecessiteDAO().findAll();
        System.out.println("Liste de skillNeeded : \n" + skillNeededList);

        // Crée la map des dépendances
        Map<Integer, List<Integer>> skillDependencies = matrixUtils.createSkillDependencyMap(skillNeededList);
        System.out.println( matrixUtils.getMapSkillToHisInt());

        // Crée la matrice d'adjacence
        int[][] adjacencyMatrix = matrixUtils.createAdjacencyMatrix(skillDependencies);

        // Affiche la matrice d'adjacence
        System.out.println("Matrice d'adjacence : \n" + matrixUtils.MatrixToString(adjacencyMatrix));


        // Teste si le graphe est orienté
        boolean oriented = GraphAlgorithms.isOriented(adjacencyMatrix);
        System.out.println("Le graphe est orienté ? " + oriented);
    }
}
