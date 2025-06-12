package model.graphCelianTest.samedia17h30.graph;

import model.dao.*;
import model.data.persistence.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignmentGreedy {

    private final ArrayList<String> competences = new ArrayList<>(Arrays.asList("PSE1", "PSE2", "SSA", "CE", "VPSP", "CP", "CO", "PBC", "PBF"));

    /**
     * Constructeur permettant d'assigner les secouristes
     * @param dps
     */
    public ArrayList<Secouriste> AssignmentGreedy(DPS dps) {

        if (dps == null) {
            throw new IllegalArgumentException("L'argument est null");
        }

        ArrayList<Competence> competencesBesoins = new BesoinDAO().findByDPS(dps).getCompetences();

        if (competencesBesoins.isEmpty()) {
            throw new IllegalArgumentException("L'argument est null");
        }

        System.out.println(new BesoinDAO().findByDPS(dps));


        ArrayList<Secouriste> secouristesAssignement = new ArrayList<>();
        Journee journee = dps.getJournee();
        List<Secouriste> secouristes = secouristesDisponible(journee);

        if (secouristes == null || secouristes.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas de secouristes de disponible");
        }

        ArrayList<ArrayList<Long>> tabSecouComp = tabSecouComp(secouristes);

        while (!competencesBesoins.isEmpty() && secouristes.size() > 0) {

            Competence competenceSelect = new Competence(this.competences.get(indiceCompetenceSelectionne(tabSecouComp,  competencesBesoins)));
            Secouriste secouristeSelect = SecouristeSelectionne(tabSecouComp, competencesBesoins);
            secouristesAssignement.add(secouristeSelect);
            retirerSecouristeComp(secouristeSelect, competencesBesoins, tabSecouComp);
            new AffectationDAO().insert(new Affectation(secouristeSelect, dps, competenceSelect));
        }
        return secouristesAssignement;
    }

    /**
     * Permettant de récupérer tout les secouristes disponibles
     * @param journee
     * @return
     */
    private List<Secouriste> secouristesDisponible(Journee journee) {
        List<Secouriste> secouristesJour = new SecouristeDAO().findByDay(new JourneeDAO().findIdByJour(journee.getJour(), journee.getMois(), journee.getAnnee()));
        List<Secouriste> ret = new ArrayList<>();
        for (Secouriste secouriste : secouristesJour) {
            long idJournee = new JourneeDAO().findIdByJour(journee.getJour(), journee.getMois(), journee.getAnnee());
            long idSecouriste = secouriste.getIdSecouriste();
            if (!new AffectationDAO().rescuerThisDay(idJournee, idSecouriste)) {
                ret.add(secouriste);
            }
        }

        return ret;
    }

    /**
     * Permettant d'avoi un tableau avec comme 1ère colonne : l'id du secouriste et les autres : les compétences
     * Il y a 1 quand le secouriste à la compétence et 0 si non
     * @param secouristes
     * @return
     */
    private ArrayList<ArrayList<Long>> tabSecouComp(List<Secouriste> secouristes) {
        ArrayList<ArrayList<Long>> ret = new ArrayList<>();
        if (secouristes == null || secouristes.isEmpty()) {
            throw new IllegalArgumentException("L'argument est null");
        }
        for (Secouriste secouriste : secouristes) {
            for (Competence competence : new PossessionDAO().find(secouriste).getCompetencesSec()) {
                ArrayList<Long> list = new ArrayList<>();
                list.add(secouriste.getIdSecouriste());
                for (int i = 0; i < this.competences.size(); i++) {
                    if (this.competences.get(i).equals(competence.getIntitule())) {
                        list.add(1L);
                    } else {
                        list.add(0L);
                    }
                }
                ret.add(list);
            }
        }

        return ret;
    }

    /**
     * On récupère l'indice de la competence avec le moins de secouristes assignés dans l'attribut competence
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
     */
    private int indiceCompetenceSelectionne(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        long[] compNombre = nombreCompetences(tabSecouComp);
        int ret = 0;
        long valMin = Long.MAX_VALUE;

        for (int i = 0; i < compNombre.length; i++) {
            if (competencesUtiles.contains(this.competences.get(i))) {
                if (compNombre[i] < valMin) {
                    valMin = compNombre[i];
                    ret = i;
                }
            }
        }

        return ret;
    }

    /**
     * Permet de créer un tableau avec pour chaque indices de compétences
     * le nombre d'occurences qu'il apparait chez les secouristes
     * @param tabSecouComp
     * @return
     */
    private long[] nombreCompetences(ArrayList<ArrayList<Long>> tabSecouComp) {
        long[] ret = new long[competences.size()];

        for (ArrayList<Long> list : tabSecouComp) {
            for (int i = 1; i < list.size(); i++) {
                ret[i] += list.get(i);
            }
        }

        return ret;
    }

    /**
     * Sélectionne les secouristes qui ont la compétence la moins présente
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
     */
    private ArrayList<ArrayList<Long>> SecouristesSelectionnes(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        ArrayList<ArrayList<Long>> ret = new ArrayList<>();
        int indCompMoinsRepresente = indiceCompetenceSelectionne(tabSecouComp, competencesUtiles);

        for (ArrayList<Long> list : tabSecouComp) {
            if  (list.get(indCompMoinsRepresente) == 1) {
                ret.add(list);
            }
        }

        return ret;
    }

    /**
     * Permet de Récupéré le secouriste qui a le moins de compétence dans les secouristes séléctionnés
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
     */
    private Secouriste SecouristeSelectionne(ArrayList<ArrayList<Long>> tabSecouComp, ArrayList<Competence> competencesUtiles) {
        ArrayList<ArrayList<Long>> secouristes = SecouristesSelectionnes(tabSecouComp, competencesUtiles);
        int indMin = 0;
        long valMin = Long.MAX_VALUE;

        for (int i = 0; i < tabSecouComp.size(); i++) {
            ArrayList<Long> list = secouristes.get(i);
            int somme = 0;
            for (int x = 1; x < list.size(); x++) {
                somme += list.get(x);
            }
            if (somme < valMin) {
                valMin = somme;
                indMin = i;
            }
        }

        return new SecouristeDAO().findById(indMin);
    }

    /**
     * Permet après avoir trouvé le secouriste de le supprimer de la liste et d'enlever
     * la competence utile qui a été attribué
     * @param secouriste
     * @param competencesUtiles
     * @param tabSecouComp
     */
    private void retirerSecouristeComp (Secouriste secouriste, ArrayList<Competence> competencesUtiles, ArrayList<ArrayList<Long>> tabSecouComp) {
        for (int i = 0; i < tabSecouComp.size(); i++) {
            if (tabSecouComp.get(i).get(0) == secouriste.getIdSecouriste()) {
                tabSecouComp.remove(i);
            }
        }

        int indiceCompetence = indiceCompetenceSelectionne(tabSecouComp, competencesUtiles);
        int taille = competencesUtiles.size();
        while (competencesUtiles.size() == taille) {
            if (competencesUtiles.contains(this.competences.get(indiceCompetence))) {
                competencesUtiles.remove(indiceCompetence);
            }
        }
    }
}
