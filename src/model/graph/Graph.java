package model.graph;

public interface Graph<N> {
    void addNode(N node);
    void addEdge(N from, N to);
    boolean hasEdge(N from, N to);
    int getNodeCount();
    int getEdgeCount();
    java.util.Set<N> getNodes();
    java.util.Set<N> getSuccessors(N node);
}
