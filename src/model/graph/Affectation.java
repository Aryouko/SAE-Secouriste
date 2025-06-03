package model.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Affectation - It distributes the rescuers of the French rescuers association
 */
public class Affectation {

    /**
     * It checks if the adjacency matrix is valid
     * @param matrice - the matrix given
     * @return true is the adjacency matrix is valid, false otherwise
     */
    public boolean matriceAdjValide(int[][] matrice) {
        boolean ret = true;

        if(matrice == null) {
            ret = false;
        } else {
            for (int i = 0; i < matrice.length; i++) {
                if (matrice[i].length != matrice.length) {
                    ret = false;
                }
            }
        }
        return ret;
    }

    /**
     * It checks if the adjacency matrix is oriented
     * @param matriceAdj - the adjacency matrix given
     * @return true if the adjacency matrix is oriented, false otherwise
     * @throws IllegalArgumentException if the adjency matrix is not valid
     */
    public boolean estOriente(int[][] matriceAdj) throws IllegalArgumentException {

        if (!matriceAdjValide(matriceAdj)) {
            throw new IllegalArgumentException("Matrice d'adjacence invalide.");
        }

        boolean ret = false;
        for (int i = 0; i < matriceAdj.length; i++) {
            for (int j = 0; j < matriceAdj.length; j++) {
                if (matriceAdj[i][j] != matriceAdj[j][i]) {
                    ret = true;
                }
            }
        }
        return ret;
    };

    /**
     * It checks if there is return arc in the adjacency matrix
     * @param matriceAdj - the adjacency matrix given
     * @return true if the adjacency matrix contains return arc, false otherwise
     * @throws IllegalArgumentException if the adjency matrix is not valid
     */
    public boolean arcsRetours(int[][] matriceAdj) throws IllegalArgumentException {

        if (!matriceAdjValide(matriceAdj)) {
            throw new IllegalArgumentException("Matrice d'adjacence invalide.");
        }

        boolean ret = false;
        ResultatDfs resultat = dfs(matriceAdj);
        int[] debut = resultat.getDebut();
        int[] fin = resultat.getFin();

        if (matriceAdjValide(matriceAdj)) {
            for (int u = 0; u < matriceAdj.length; u++) {
                for (int v = 0; v < matriceAdj.length; v++) {
                    if (matriceAdj[u][v] != 0 && debut[v] < debut[u] && fin[u] < fin[v]) {
                        ret = true;
                    }
                }
            }
        } else {
            ret = false;
        }
        return ret;
    };

    /**
     * DFS traversal of a graph (main fonction)
     * @param matriceAdj - the adjacency matrix given
     * @return the result of the dfs with the 4 components
     * @throws IllegalArgumentException if the adjency matrix is not valid
     */
    public ResultatDfs dfs(int[][] matriceAdj) throws IllegalArgumentException {

        if (!matriceAdjValide(matriceAdj)) {
            throw new IllegalArgumentException("Matrice d'adjacence invalide.");
        }

        int n = matriceAdj.length;
        int[] etatSommet = new int[n];
        int[] parent = new int[n];
        int[] debut = new int[n];
        int[] fin = new int[n];
        int[][] arborescence = new int[n][n];
        int[] temps = {0};

        // Initialisation
        for (int i = 0; i < n; i++) {
            etatSommet[i] = -1;
            parent[i] = -1;
            debut[i] = -1;
            fin[i] = -1;
            for (int j = 0; j < n; j++) {
                arborescence[i][j] = 0;
            }
        }

        // DFS depuis chaque sommet non visité
        for (int sommet = 0; sommet < n; sommet++) {
            if (etatSommet[sommet] == -1) {
                dfsRec(matriceAdj, sommet, etatSommet, parent, debut, fin, arborescence, temps);
            }
        }
        return new ResultatDfs(parent,arborescence,debut,fin);
    }

    /**
     * DFS traversal of a graph (auxilliary function)
     * @param matrice - the adjacency matrix
     * @param sommet - current vertex
     * @param etat - the array of state of each vertex in the DFS tree
     * @param parent - the array of parent of each vertex in the DFS tree
     * @param debut - the array of times each vertex are seen for the first time in the traversal
     * @param fin - the array of times each vertex are seen for the last time in the traversal
     * @param arbre - the tree
     * @param temps - the time counter
     */
    public void dfsRec(int[][] matrice,int sommet,int[] etat,int[] parent,int[] debut,int[] fin,int[][] arbre,int[] temps) {
        etat[sommet] = 0;
        debut[sommet] = ++temps[0];

        for (int voisin = 0; voisin < matrice.length; voisin++) {
            if (matrice[sommet][voisin] != 0) {
                if (etat[voisin] == -1) {
                    parent[voisin] = sommet;
                    arbre[sommet][voisin] = 1;
                    dfsRec(matrice, voisin, etat, parent, debut, fin, arbre, temps);
                }
            }
        }
        etat[sommet] = 1;
        fin[sommet] = ++temps[0];
    }

    /**
     * It checks if the adjacency matrix is a DAG (directed acyclic graph)
     * @param matriceAdj - the adjacency matrix given
     * @return true if the adjacency matrix is a DAG, false othewise
     * @throws IllegalArgumentException if the adjency matrix is not valid
     */
    public boolean estDag(int[][] matriceAdj) throws IllegalArgumentException {
        if (!matriceAdjValide(matriceAdj)) {
            throw new IllegalArgumentException("La matrice n’est pas valide.");
        } else {
            return estOriente(matriceAdj) && !arcsRetours(matriceAdj);
        }
    }

    /*
    public int[][] approcheGloutonne() {
        int[][] ret;

        return ret;
    }

    public int[][] approcheExhaustive() {
        int[][] ret;

        return ret;
    }

     */

    public boolean affectationValide(int[][] matriceAffect) {
        boolean ret = true;

        return ret;
    }
}
