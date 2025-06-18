package model.graph.test;

import model.dao.DPSDAO;
import model.graph.AssignmentGreedy;
import model.data.persistence.*;

import java.util.ArrayList;

public class TestAssignmentGreedy {

    public static void main(String[] args) {
        DPS dps = new DPSDAO().findById(1); // exemple DPS avec ID 1
        AssignmentGreedy greedy = new AssignmentGreedy();

        long start = System.nanoTime();
        ArrayList<Secouriste> result = greedy.AssignmentRescuersGreedy(dps);
        long end = System.nanoTime();

        System.out.println("=== GREEDY ASSIGNMENT ===");
        System.out.println("Temps d’exécution : " + (end - start) / 1e6 + " ms");
        System.out.println("Secouristes affectés : " + result.size());
        for (Secouriste s : result) {
            System.out.println("- " + s.getNom() + " " + s.getPrenom());
        }
    }
}
