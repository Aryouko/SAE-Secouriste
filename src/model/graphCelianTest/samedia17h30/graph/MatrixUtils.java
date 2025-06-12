package model.graphCelianTest.samedia17h30.graph;

import model.data.persistence.Competence;
import model.data.persistence.Necessite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixUtils {

    // Associe chaque compétence à un entier unique
    private final Map<Competence, Integer> MapSkillToHisInt = new HashMap<>();


    public Map<Integer, List<Integer>> createSkillDependencyMap(List<Necessite> elements) {
        Map<Integer, List<Integer>> skillDependencies = new HashMap<>();
        int index = 0;

        for (Necessite n : elements) {
            Competence c1 = n.getComp1();
            Competence c2 = n.getComp2();

            if (!MapSkillToHisInt.containsKey(c1)) {
                MapSkillToHisInt.put(c1, index++);
            }
            if (!MapSkillToHisInt.containsKey(c2)) {
                MapSkillToHisInt.put(c2, index++);
            }
        }

        for (Necessite n : elements) {
            int from = MapSkillToHisInt.get(n.getComp1());
            int to = MapSkillToHisInt.get(n.getComp2());

            skillDependencies.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }

        return skillDependencies;
    }





    /*
    public static int[][] createAdjacencyMatrix(List<Necessite> elements) {

        // All the variables needed : size of the list = n and matrix full of 0
        int n = elements.size();
        int[][] matrix = new int[n][n];

        // Filling the matrix
        for (Necessite skillNeeded : elements) {

            for (int i=0 ; i<n ; i++) {

            }
        }
    }
     */
}
