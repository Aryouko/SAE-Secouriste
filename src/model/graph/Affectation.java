package model.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Affectation - It distributes the rescuers of the French rescuers association
 */
public class Affectation {

    /**
     * It checks if the adjacency matrix is valid
     * @param matrix - the matrix given
     * @return true is the adjacency matrix is valid, false otherwise
     */
    public boolean adjMatrixValid(int[][] matrix) {

        if(matrix == null) {
            return false;
        } else {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i].length != matrix.length) {
                    return false;
                }
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] != 0 && matrix[i][j] != 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * It checks if the adjacency matrix is oriented
     * @param adjMatrix - the adjacency matrix given
     * @return true if the adjacency matrix is oriented, false otherwise
     */
    public boolean isOriented(int[][] adjMatrix) {

        if (!adjMatrixValid(adjMatrix)) {
            return false;
        }

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] != adjMatrix[j][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * It checks if the graph is a DAG (Directed acyclic graph)
     * @param adjMatrix - the adjacency matrix given
     * @return true if the graph is a DAG, false otherwise
     */
    public boolean isDAG(int[][] adjMatrix){

        if (!isOriented(adjMatrix)) {
            return false;
        }

        boolean[] visited = new boolean[adjMatrix.length];
        boolean[] inProcess = new boolean[adjMatrix.length];

        for (int i = 0; i < adjMatrix.length; i++) {
            if (!visited[i]) {
                if (cycleDetected(adjMatrix, i, visited, inProcess)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * It checks if e cycle is detected during the DFS
     * @param adjMatrix - the adjacency matrix given
     * @param v - the vertex we are going to check
     * @param visited - the list that contains true if a vertex is visited, false otherwise
     * @param inProcess - the list that contains true if a vertex is going to be studied from the current vertex, false otherwise
     * @return true if a cycle is detected, false otherwise
     */
    private boolean cycleDetected(int[][] adjMatrix, int v, boolean[] visited, boolean[] inProcess) {
        visited[v] = true;
        inProcess[v] = true;

        for (int i = 0; i < adjMatrix.length; i++) {
            if (adjMatrix[v][i] == 1) {
                if (!visited[i]) {
                    if (cycleDetected(adjMatrix, i, visited, inProcess)) return true;
                } else if (inProcess[i]) {
                    return true;
                }
            }
        }

        inProcess[v] = false;
        return false;
    }
}
