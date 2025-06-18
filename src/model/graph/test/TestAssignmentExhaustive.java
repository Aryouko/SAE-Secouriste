package model.graph.test;

import model.dao.BesoinDAO;
import model.dao.DPSDAO;
import model.graph.AssignmentExhaustive;
import model.data.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestAssignmentExhaustive {

    public static void main(String[] args) {
        DPS dps = new DPSDAO().findById(1); // même DPS que dans le test Greedy
        ArrayList<Competence> competences = new BesoinDAO().findByDPS(dps).getCompetences();

        long start = System.nanoTime();
        AssignmentExhaustive exhaustive = new AssignmentExhaustive(dps, competences);
        long end = System.nanoTime();

        HashMap<Competence, Secouriste> affectation = exhaustive.getAffectation();

        System.out.println("=== EXHAUSTIVE ASSIGNMENT ===");
        System.out.println("Temps d’exécution : " + (end - start) / 1e6 + " ms");
        System.out.println("Affectations : ");
        for (Competence comp : affectation.keySet()) {
            Secouriste s = affectation.get(comp);
            System.out.println("- " + comp.getIntitule() + " => " + s.getNom() + " " + s.getPrenom());
        }
    }
}
