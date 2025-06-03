package model.graph;

import model.data.persistence.Competence;
import model.data.persistence.Secouriste;

import java.util.ArrayList;
import java.util.List;


public class Graphe {
    private List<Secouriste> secouristes;
    private List<Competence> competences;
    private boolean[][]      adjencyMatrix;

    // ----- Constructeurs -----

    /**
     * Construit un graphe vide (sans sommets).
     * Il faudra ajouter d’abord les sommets, puis appeller initialiserMatrice().
     */
    public Graphe() {
        this.secouristes = new ArrayList<>();
        this.competences = new ArrayList<>();
        this.adjencyMatrix   = null;
    }

    /**
     * Construit un graphe à partir de listes pré-existantes.
     * La matrice est immédiatement initialisée.
     */
    public Graphe(List<Secouriste> listeSec, List<Competence> listeComp) {
        this.secouristes = new ArrayList<>(listeSec);
        this.competences = new ArrayList<>(listeComp);
        initialiserMatrice();
    }

    // ----- Méthodes de gestion des sommets -----

    /**
     * Ajoute un secouriste dans la liste.
     * La matrice n'est pas modifiée immédiatement :
     * appeler initialiserMatrice() après avoir ajouté tous les sommets.
     */
    public void ajouterSecouriste(Secouriste s) {
        secouristes.add(s);
        // On invalide ou reconstruit la matrice dès que possible
        this.adjencyMatrix = null;
    }

    /**
     * Ajoute une compétence dans la liste.
     * La matrice n'est pas modifiée immédiatement :
     * appeler initialiserMatrice() après avoir ajouté tous les sommets.
     */
    public void ajouterCompetence(Competence c) {
        competences.add(c);
        // On invalide ou reconstruit la matrice dès que possible
        this.adjencyMatrix = null;
    }

    /**
     * Initialise (ou ré-initialise) la matrice d'adjacence
     * selon la taille actuelle des listes secouristes/competences.
     * Toutes les liaisons sont remises à false.
     */
    public void initialiserMatrice() {
        int n = secouristes.size();
        int m = competences.size();
        this.adjencyMatrix = new boolean[n][m];
        // Par défaut, toutes les entrées sont false (aucune arête).
    }

    // ----- Méthodes de gestion des arêtes -----

    /**
     * Lie le secouriste (index idxSec) à la compétence (index idxComp).
     * Supposé que la matrice a déjà été initialisée.
     */
    public void lier(int idxSec, int idxComp) {
        if (adjencyMatrix == null) {
            throw new IllegalStateException("La matrice n'est pas initialisée. Appelez initialiserMatrice() d'abord.");
        }
        if (idxSec < 0 || idxSec >= secouristes.size() ||
                idxComp < 0 || idxComp >= competences.size()) {
            throw new IndexOutOfBoundsException("Indices invalides pour lier.");
        }
        adjencyMatrix[idxSec][idxComp] = true;
    }

    /**
     * Même opération, mais on fournit directement les objets Secouriste et Competence.
     * On recherche leurs index dans les listes, puis on appelle lier(idxSec, idxComp).
     */
    public void lier(Secouriste s, Competence c) {
        int idxSec = secouristes.indexOf(s);
        int idxComp = competences.indexOf(c);
        if (idxSec == -1 || idxComp == -1) {
            throw new IllegalArgumentException("Secouriste ou compétence introuvable.");
        }
        lier(idxSec, idxComp);
    }

    /**
     * Supprime la liaison entre le secouriste idxSec et la compétence idxComp.
     */
    public void delier(int idxSec, int idxComp) {
        if (adjencyMatrix == null) {
            throw new IllegalStateException("La matrice n'est pas initialisée. Appelez initialiserMatrice() d'abord.");
        }
        if (idxSec < 0 || idxSec >= secouristes.size() ||
                idxComp < 0 || idxComp >= competences.size()) {
            throw new IndexOutOfBoundsException("Indices invalides pour delier.");
        }
        adjencyMatrix[idxSec][idxComp] = false;
    }

    // ----- Méthodes d’accès aux voisins -----

    /**
     * Renvoie la liste des compétences reliées au secouriste d’index idxSec.
     */
    public List<Competence> getVoisinsSecouriste(int idxSec) {
        if (adjencyMatrix == null) {
            throw new IllegalStateException("La matrice n'est pas initialisée.");
        }
        if (idxSec < 0 || idxSec >= secouristes.size()) {
            throw new IndexOutOfBoundsException("Index secouriste invalide.");
        }
        List<Competence> voisins = new ArrayList<>();
        for (int j = 0; j < competences.size(); j++) {
            if (adjencyMatrix[idxSec][j]) {
                voisins.add(competences.get(j));
            }
        }
        return voisins;
    }

    /**
     * Renvoie la liste des secouristes reliés à la compétence d’index idxComp.
     */
    public List<Secouriste> getVoisinsCompetence(int idxComp) {
        if (adjencyMatrix == null) {
            throw new IllegalStateException("La matrice n'est pas initialisée.");
        }
        if (idxComp < 0 || idxComp >= competences.size()) {
            throw new IndexOutOfBoundsException("Index compétence invalide.");
        }
        List<Secouriste> voisins = new ArrayList<>();
        for (int i = 0; i < secouristes.size(); i++) {
            if (adjencyMatrix[i][idxComp]) {
                voisins.add(secouristes.get(i));
            }
        }
        return voisins;
    }

    // ----- Méthodes utilitaires -----

    /**
     * Affiche la matrice d’adjacence dans la console (pour débug).
     */
    public void afficherMatrice() {
        if (adjencyMatrix == null) {
            System.out.println("La matrice n'est pas initialisée.");
            return;
        }
        System.out.println("    ‖ Compétences ‖");
        System.out.print("       ");
        for (int j = 0; j < competences.size(); j++) {
            System.out.printf("%4s", j);
        }
        System.out.println();
        System.out.println("--------------------------------");
        for (int i = 0; i < secouristes.size(); i++) {
            System.out.printf("Sec%2d | ", i);
            for (int j = 0; j < competences.size(); j++) {
                System.out.print(adjencyMatrix[i][j] ? "  1 " : "  0 ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Retourne une représentation textuelle simple.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graphe biparti :\n");
        sb.append("  Secouristes (").append(secouristes.size()).append(") :\n");
        for (int i = 0; i < secouristes.size(); i++) {
            sb.append("   [").append(i).append("] ").append(secouristes.get(i)).append("\n");
        }
        sb.append("  Compétences (").append(competences.size()).append(") :\n");
        for (int j = 0; j < competences.size(); j++) {
            sb.append("   [").append(j).append("] ").append(competences.get(j)).append("\n");
        }
        sb.append("  Arêtes (liens) :\n");
        if (adjencyMatrix == null) {
            sb.append("   (matrice non initialisée)\n");
        } else {
            for (int i = 0; i < secouristes.size(); i++) {
                for (int j = 0; j < competences.size(); j++) {
                    if (adjencyMatrix[i][j]) {
                        sb.append("   Secouriste #").append(i)
                                .append(" <--> Compétence #").append(j).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    // ----- Getters / Setters si besoin -----

    public List<Secouriste> getSecouristes() {
        return secouristes;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public boolean[][] getAdjencyMatrix() {
        return adjencyMatrix;
    }
}
