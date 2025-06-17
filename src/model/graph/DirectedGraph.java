package model.graph;

import static model.graph.GraphAlgorithms.isOriented;
import static model.graph.GraphAlgorithms.cycleDetected;

public class DirectedGraph {

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
}
