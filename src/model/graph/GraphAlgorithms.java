package model.graph;

/**
 * GraphAlgorithms - This class provides utility methods for working with graphs,
 * including checking if a graph is oriented, detecting cycles in a directed graph,
 * and validating adjacency matrices.
 */
public class GraphAlgorithms {


    /**
     * It checks if the graph is oriented
     * @param matrix - the adjacency matrix given
     * @return true if the graph is oriented, false otherwise
     */
    public static boolean isOriented(int[][] matrix) {
        int indexL = 0 ;
        for (int[] line : matrix) {

            // For each line, we check if there is a column with a value of 1
            int indexC = 0;
            for (int column : line) {

                // If the value is 1, we check if the corresponding column has a value of 1 in the opposite direction
                if (column == 1 ) {
                    if ( matrix[indexC][indexL] == 1 ) {
                        return false ; // If there is a value of 1 in the opposite direction, the graph is not oriented
                    }
                }
                indexC++; // Increment the column index
            }
            indexL++; // Increment the line index
        }
        return true;
    }


    /**
     * It checks if z cycle is detected during the DFS
     * @param adjMatrix - the adjacency matrix
     * @param v - the vertex we are going to check
     * @param visited - the list that contains true if a vertex is visited, false otherwise
     * @param inProcess - the list that contains true if a vertex is going to be studied from the current vertex, false otherwise
     * @return true if a cycle is detected, false otherwise
     */
    public static boolean cycleDetected(int[][] adjMatrix, int v, boolean[] visited, boolean[] inProcess) {
        visited[v] = true; // Mark the current vertex v as visited in the visited list
        inProcess[v] = true; // Mark the vertex as in process

        for (int i = 0; i < adjMatrix.length; i++) { // Iterate through all vertices using the adjacency matrix
            if (adjMatrix[v][i] == 1) { // Check if there is an edge from vertex v to vertex i
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
}
