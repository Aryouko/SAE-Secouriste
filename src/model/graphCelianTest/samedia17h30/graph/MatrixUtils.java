package model.graphCelianTest.samedia17h30.graph;

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

        // Flemme d'écrire la suite en Anglais :
        // ForEach renvoie type BiConsumer : En gros, on interagit avec une methode sur une deux arguments (par exemple key, values). c'est une boucle forEach pour un dico
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
}
