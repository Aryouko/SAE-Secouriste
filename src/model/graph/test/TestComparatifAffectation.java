package model.graph.test;

import model.dao.BesoinDAO;
import model.dao.DPSDAO;
import model.graph.AssignmentGreedy;
import model.graph.AssignmentExhaustive;
import model.data.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestComparatifAffectation {

    /*
    public static void main(String[] args) {
        int[] idsDps = {1, 2, 3}; // différents scénarios

        for (int id : idsDps) {
            System.out.println("==== TEST DPS ID " + id + " ====");
            DPS dps = new DPSDAO().findById(id);
            ArrayList<Competence> competences = new BesoinDAO().findByDPS(dps).getCompetences();

            // GREEDY
            AssignmentGreedy greedy = new AssignmentGreedy();
            long startGreedy = System.nanoTime();
            ArrayList<Secouriste> resGreedy = greedy.AssignmentRescuersGreedy(dps);
            long endGreedy = System.nanoTime();

            // EXHAUSTIVE
            long startExh = System.nanoTime();
            AssignmentExhaustive exhaustive = null;
            try {
                exhaustive = new AssignmentExhaustive(dps, competences);
            } catch (Exception e) {
                System.out.println("Exhaustive échoué : " + e.getMessage());
            }
            long endExh = System.nanoTime();

            System.out.println("→ Temps Greedy : " + (endGreedy - startGreedy) / 1e6 + " ms");
            System.out.println("→ Résultat Greedy : " + resGreedy.size() + " secouristes");

            if (exhaustive != null) {
                HashMap<Competence, Secouriste> resExh = exhaustive.getAffectation();
                System.out.println("→ Temps Exhaustive : " + (endExh - startExh) / 1e6 + " ms");
                System.out.println("→ Résultat Exhaustive : " + resExh.size() + " compétences couvertes");
            }

            System.out.println("\n");
        }
    }
    */

}
