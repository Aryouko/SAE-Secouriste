package model.graph.test;

import model.dao.DPSDAO;
import model.data.persistence.Secouriste;
import model.graph.AssignmentGreedy;

import java.util.ArrayList;

/**
 * Tests of Affectation's methods
 */
public class AssignmentTest {
    public static void main(String[] args) {
        AssignmentGreedy assignmentGreedy = new AssignmentGreedy();
        try {
            ArrayList<Secouriste> secouristes = assignmentGreedy.AssignmentRescuersGreedy(new DPSDAO().findAll().get(0));
            System.out.println("secouristes size: " + secouristes.size());
            for (Secouriste secouriste : secouristes) {
                System.out.println(secouriste.getNom() + " " + secouriste.getPrenom());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    //TODO
}
