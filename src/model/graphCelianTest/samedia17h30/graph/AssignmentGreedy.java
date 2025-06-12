package model.graphCelianTest.samedia17h30.graph;

import model.dao.CompetenceDAO;
import model.dao.DisponibiliteDAO;
import model.dao.PossessionDAO;
import model.dao.SecouristeDAO;
import model.data.persistence.Competence;
import model.data.persistence.DPS;
import model.data.persistence.Journee;
import model.data.persistence.Secouriste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignmentGreedy {

    private ArrayList<String> competences = new ArrayList<>(Arrays.asList("PSE1", "PSE2", "SSA", "CE", "VPSP", "CP", "CO", "PBC", "PBF"));

    private ArrayList<Secouriste> secouristesAssignement;

    public AssignmentGreedy(DPS dps, ArrayList<Competence> competencesUtiles) {
        if (dps == null || competencesUtiles == null) {
            throw new IllegalArgumentException("Les arguments ne peut pas null");
        }
        this.secouristesAssignement = new ArrayList<>();
        Journee journee = dps.getJournee();
        List<Secouriste> secouristes = new SecouristeDAO().findByDay(new DisponibiliteDAO().findIdByJour(journee.getJour(), journee.getMois(), journee.getAnnee()));
        if (secouristes == null) {
            throw new IllegalArgumentException("Il n'y a pas de secouristes de disponible");
        }
        ArrayList<ArrayList<Long>> tabSecouComp = tabSecouComp(secouristes);
        while (!competencesUtiles.isEmpty()) {
            Secouriste secouristeSelect = SecouristeSelectionne(tabSecouComp, competencesUtiles);
            this.secouristesAssignement.add(secouristeSelect);
            retirerSecouristeComp(secouristeSelect, competencesUtiles, tabSecouComp);

        }
    }

    private ArrayList<ArrayList<Long>> tabSecouComp(List<Secouriste> secouristes) {
        ArrayList<ArrayList<Long>> ret = new ArrayList<>();

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

    private long[] nombreCompetences(ArrayList<ArrayList<Long>> tabSecouComp) {
        long[] ret = new long[competences.size()];

        for (ArrayList<Long> list : tabSecouComp) {
            for (int i = 1; i < list.size(); i++) {
                ret[i] += list.get(i);
            }
        }

        return ret;
    }

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
