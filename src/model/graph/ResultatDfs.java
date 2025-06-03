package src.model.graph;

import java.util.ArrayList;
import java.util.List;

public class ResultatDfs {

    /**
     * Contains the parent of each vertex in the depth tree
     */
    private int[] parent;

    /**
     * Adjacency matrix of the edges of the DFS tree
     */
    private int[][] arborescence;

    /**
     * The time when each vertex is discovered
     */
    private int[] debut;

    /**
     * The time when the visit to each vertex is completed
     */
    private int[] fin;

    public ResultatDfs(int[] parent, int[][] arborescence, int[] debut, int[] fin) {
        this.parent = parent;
        this.arborescence = arborescence;
        this.debut = debut;
        this.fin = fin;
    }

    /**
     * Getter
     */
    public int[] getDebut() {
        int[] ret = new int[this.debut.length];
        for (int i = 0; i < this.debut.length; i++) {
            ret[i] = this.debut[i];
        }
        return ret;
    }

    /**
     * Getter
     */
    public int[] getFin() {
        int[] ret = new int[this.fin.length];
        for (int i = 0; i < this.fin.length; i++) {
            ret[i] = this.fin[i];
        }
        return ret;
    }

    /**
     * Getter
     */
    public int[][] getArborescence() {
        int[][] ret = new int[this.arborescence.length][this.arborescence[0].length];
        for (int i = 0; i < this.arborescence.length; i++) {
            for (int j = i + 1; j < this.arborescence.length; j++) {
                ret[i][j] = this.arborescence[i][j];
            }
        }
        return ret;
    }

    /**
     * Getter
     */
    public int[] getParent() {
        int[] ret = new int[this.parent.length];
        for (int i = 0; i < this.parent.length; i++) {
            ret[i] = this.parent[i];
        }
        return ret;
    }
};