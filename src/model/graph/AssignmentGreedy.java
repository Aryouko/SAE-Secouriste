package model.graph;

import model.dao.CompetenceDAO;
import model.data.persistence.*;
import model.data.service.AffectationManagement;
import model.data.service.BesoinManagement;
import model.data.service.JourneeManagement;
import model.data.service.SecouristeManagement;
import model.data.service.PossessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for assigning rescuers to DPS by allocating the least represented skill
 * to the DPS that requires this skill and has the fewest skills.
 *
 * @author C.Brocart, T.Brami-Coatual, L.Carré, G.Potay
 * @version 1.0
 */
public class   AssignmentGreedy {

    private final CompetenceDAO competenceDAO = new CompetenceDAO() ;

    private ArrayList<String> competences = (ArrayList<String>) competenceDAO.findAllIntitule();

    private final BesoinManagement besoinManagement = new BesoinManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    private final JourneeManagement journeeManagement = new JourneeManagement();

    private final PossessionManagement possessionManagement = new PossessionManagement();

    /**
     * Assigns rescuers to a DPS (First Aid Post) in the most optimal way.
     *
     * @param dps - a DPS (First Aid Post)
     */
    public ArrayList<Secouriste> AssignmentRescuersGreedy(DPS dps){

        if (dps == null) {
            throw new IllegalArgumentException("L'argument est null");
        }

        ArrayList<Competence> competencesBesoins = this.besoinManagement.getBesoinByDPS(dps).getCompetences();
        if (competencesBesoins.isEmpty()) {
            throw new IllegalArgumentException("L'argument est null");
        }

        ArrayList<Secouriste> secouristesAssignement = new ArrayList<>();
        Journee journee = dps.getJournee();
        List<Secouriste> secouristes = secouristesDisponible(journee);

        if (secouristes.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas de secouristes de disponible");
        }

        ArrayList<ArrayList<Long>> tabSecouComp = tabSecouComp(secouristes);
        try {
            while (!competencesBesoins.isEmpty() && !tabSecouComp.isEmpty()) {
                int indiceComp = indiceCompetenceSelectionne(tabSecouComp, competencesBesoins);
                String compIntitule = this.competences.get(indiceComp);
                Competence competenceSelect = new Competence(compIntitule);

                Secouriste secouristeSelect = SecouristeSelectionne(tabSecouComp, competencesBesoins);

                if (secouristeSelect == null) {
                    for (int i = 0; i < competencesBesoins.size(); i++) {
                        if (competencesBesoins.get(i).getIntitule().equals(compIntitule)) {
                            competencesBesoins.remove(i);
                            break;
                        }
                    }
                } else {
                    secouristesAssignement.add(secouristeSelect);

                    Affectation affectation = new Affectation(secouristeSelect, dps, competenceSelect);
                    if (!this.affectationManagement.isExist(affectation)) {
                        this.affectationManagement.addAffectation(affectation);
                        this.besoinManagement.deleteBesoinByDPSAndCompetence(dps, competenceSelect);
                    }
                    retirerSecouristeComp(secouristeSelect, competencesBesoins, tabSecouComp, competenceSelect);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return secouristesAssignement;
    }

    /**
     * Retrieves all available rescuers.
     *
     * @param journee - a day
     * @return a list of available rescuers
     */
    private List<Secouriste> secouristesDisponible(Journee journee) {
        List<Secouriste> secouristesJour = this.secouristeManagement.findByIdJournee(this.journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee()));
        List<Secouriste> ret = new ArrayList<>();

        for (Secouriste secouriste : secouristesJour) {
            long idJournee = this.journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee());
            long idSecouriste = secouriste.getIdSecouriste();
            if (this.affectationManagement.rescuerAvailable(idJournee, idSecouriste)) {
                ret.add(secouriste);
            }
        }
        return ret;
    }

    /**
     * Creates a table where the first column contains the rescuer ID,
     * and the following columns represent skills.
     * A value of 1 indicates that the rescuer has the skill, and 0 otherwise.
     *
     * @param secouristes - list of rescuers
     * @return a table of rescuers and their associated skills
     */
    private ArrayList<ArrayList<Long>> tabSecouComp(List<Secouriste> secouristes) throws IllegalArgumentException {
        ArrayList<ArrayList<Long>> ret = new ArrayList<>();
        if (secouristes == null || secouristes.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas/plus de secouristes de disponible");
        }
        for (Secouriste secouriste : secouristes) {
            ArrayList<Long> list = new ArrayList<>();
            list.add(secouriste.getIdSecouriste());

            ArrayList<String> compSecouriste = new ArrayList<>();
            for (Competence competence : this.possessionManagement.getPossessionBySecouriste(secouriste).getCompetencesSec()) {
                compSecouriste.add(competence.getIntitule());
            }

            for (String competence : competences) {
                if (compSecouriste.contains(competence)) {
                    list.add(1L);
                } else {
                    list.add(0L);
                }
            }
            ret.add(list);
            }
        return ret;
    }

    /**
     * Retrieves the index of the skill with the fewest rescuers assigned
     * in the 'competencesUtiles' attribute.
     *
     * @param tabSecouComp - cross-referenced table of rescuers and skills
     * @param competencesUtiles - required skills
     * @return the index of the skill with the fewest rescuers assigned
     */
    private int indiceCompetenceSelectionne(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        long[] compNombre = nombreCompetences(tabSecouComp);
        int ret = 0;
        long valMin = Long.MAX_VALUE;

        ArrayList<String> competencesUtilesString = new ArrayList<>();
        for (Competence competence : competencesUtiles) {
            competencesUtilesString.add(competence.getIntitule());
        }

        for (int i = 0; i < compNombre.length; i++) {
            if (competencesUtilesString.contains(this.competences.get(i))) {
                if (compNombre[i] < valMin) {
                    valMin = compNombre[i];
                    ret = i;
                }
            }
        }
        return ret;
    }

    /**
     * Creates an array where each index corresponds to a skill and contains
     * the number of times it appears among the rescuers.
     *
     * @param tabSecouComp - cross-referenced table of rescuers and skills
     * @return a list where each skill is associated with its occurrence count
     */
    private long[] nombreCompetences(ArrayList<ArrayList<Long>> tabSecouComp) {
        long[] ret = new long[competences.size()];

        for (ArrayList<Long> list : tabSecouComp) {
            for (int i = 1; i < list.size(); i++) {
                ret[i - 1] += list.get(i);
            }
        }

        return ret;
    }

    /**
     * Selects the rescuers who have the least common skill.
     *
     * @param tabSecouComp - cross-referenced table of rescuers and skills
     * @param competencesUtiles - required skills
     * @return a list of rescuers
     */
    private ArrayList<ArrayList<Long>> secouristesSelectionnes(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        ArrayList<ArrayList<Long>> ret = new ArrayList<>();
        int indCompMoinsRepresente = indiceCompetenceSelectionne(tabSecouComp, competencesUtiles);

        for (ArrayList<Long> list : tabSecouComp) {
            if (list.get(indCompMoinsRepresente + 1) == 1) {
                ret.add(list);
            }
        }

        return ret;
    }

    /**
     * Retrieves the rescuer with the fewest skills among the selected rescuers.
     *
     * @param tabSecouComp - cross-referenced table of rescuers and skills
     * @param competencesUtiles - required skills
     * @return the rescuer with the fewest skills and one of the required skills
     */
    private Secouriste SecouristeSelectionne(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        ArrayList<ArrayList<Long>> secouristes = secouristesSelectionnes(tabSecouComp, competencesUtiles);

        Secouriste ret;
        if (secouristes.isEmpty() || secouristes.get(0).isEmpty()) {
            ret = null;
        } else {
            long idMin = secouristes.get(0).get(0);
            long valMin = Long.MAX_VALUE;

            for (ArrayList<Long> list : secouristes) {
                long id = list.get(0);
                int somme = 0;
                for (int x = 1; x < list.size(); x++) {
                    somme += list.get(x);
                }
                if (somme < valMin) {
                    valMin = somme;
                    idMin = id;
                }
            }
            ret = this.secouristeManagement.getSecouristeById(idMin);
        }
        return ret;
    }

    /**
     * Allows, after finding the rescuer, to remove them from the list and
     * remove the useful skill that was assigned.
     *
     * @param secouriste - the assigned rescuer
     * @param competencesUtiles - the required skills
     * @param tabSecouComp - cross-referenced table of rescuers and skills
     */
    private void retirerSecouristeComp (Secouriste secouriste, ArrayList<Competence> competencesUtiles, ArrayList<ArrayList<Long>> tabSecouComp, Competence competenceASuppr) {
        for (int i = 0; i < competencesUtiles.size(); i++) {
            if (competencesUtiles.get(i).getIntitule().equals(competenceASuppr.getIntitule())) {
                competencesUtiles.remove(i);
                break;
            }
        }

        for (int i = 0; i < tabSecouComp.size(); i++) {
            if (tabSecouComp.get(i).get(0) == secouriste.getIdSecouriste()) {
                tabSecouComp.remove(i);
                break;
            }
        }
    }
}
