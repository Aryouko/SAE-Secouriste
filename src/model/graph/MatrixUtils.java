package model.graph;

import model.dao.CompetenceDAO;
import model.data.persistence.Competence;
import model.data.persistence.Necessite;

import java.util.*;

public class MatrixUtils {

    // Associe chaque compétence à un entier unique
    private final Map<Competence, Integer> MapSkillToHisInt = new HashMap<>();


    /**
     * createSkillDependencyMap
     *
     * @param elements
     * @return
     */
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

            // computeIfAbsent is a function from Map that is very usefully, It verifies if a key is present in the map, if not the function is call and create new ArrayList.
            // the function return the values associate to the key. So if i call the .add after its going to add vertex link to the main vertex.
            skillDependencies.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }

        return skillDependencies;
    }








    /**
     * Create the Adjacency Matrix of a Map
     *
     * @param skillDependencies a map of int with all the link between vertices
     * @return A matrix of adjacency link
     */
    public int[][] createAdjacencyMatrix(Map<Integer, List<Integer>> skillDependencies) {
        // All the variables needed : size of the list = n and matrix full of 0
        int n = MapSkillToHisInt.size();
        int[][] matrix = new int[n][n];

        skillDependencies.forEach((key, values) -> {
            for (int val : values) {
                matrix[key][val] = 1 ;
            }
        });
        return matrix;
    }


    public String MatrixToString(int[][] matrix) {
        String text = "";
        for (int[] line : matrix) {
            for (int column : line) {
                text+= column + " ";
            }
            text+="\n";
        }

        return text ;
    }

    public Map<Competence, Integer> getMapSkillToHisInt() {
        return this.MapSkillToHisInt ;
    }

    /**
     * Build all superior dependencies for each competence
     *
     * @param competences List of competences
     * @param necessites  List of necessites defining dependencies
     * @return Map where each competence is associated with a list of its superior competences
     */
    public Map<Competence, List<Competence>> buildAllSuperiorDependencies(List<Competence> competences, List<Necessite> necessites) {
        Map<Competence, List<Competence>> result = new HashMap<>();
        for (Competence c : competences) {
            Set<Competence> visited = new HashSet<>();
            List<Competence> superiors = getAllSuperiors(c, necessites, visited);
            result.put(c, superiors);
        }
        return result;
    }

    /**
     * Recursively retrieves all superiors of a competence.
     *
     * @param c         The competence for which to find superiors
     * @param necessites List of dependencies (necessites)
     * @param visited   Set to track already visited competences to avoid cycles
     * @return List of all superiors of the given competence
     */
    private List<Competence> getAllSuperiors(Competence c, List<Necessite> necessites, Set<Competence> visited) {
        List<Competence> superiors = new ArrayList<>();
        for (Necessite n : necessites) {
            if (n.getComp1().equals(c)) {
                Competence sup = n.getComp2();
                if (visited.add(sup)) { // évite les cycles
                    superiors.add(sup);
                    superiors.addAll(getAllSuperiors(sup, necessites, visited));
                }
            }
        }
        return superiors;
    }
}
