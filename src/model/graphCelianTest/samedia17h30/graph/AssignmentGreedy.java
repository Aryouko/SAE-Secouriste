package model.graphCelianTest.samedia17h30.graph;

import model.data.persistence.*;
import model.data.service.AffectationManagement;
import model.data.service.BesoinManagement;
import model.data.service.JourneeManagement;
import model.data.service.SecouristeManagement;
import model.data.service.PossessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignmentGreedy {

    private final ArrayList<String> competences = new ArrayList<>(Arrays.asList("PSE1", "PSE2", "SSA", "CE", "VPSP", "CP", "CO", "PBC", "PBF"));

    private final BesoinManagement besoinManagement = new BesoinManagement();

    private final AffectationManagement affectationManagement = new AffectationManagement();

    private final SecouristeManagement secouristeManagement = new SecouristeManagement();

    private final JourneeManagement journeeManagement = new JourneeManagement();

    private final PossessionManagement possessionManagement = new PossessionManagement();

    /**
     * Constructeur permettant d'assigner les secouristes
     * @param dps - a DPS
     */
    public ArrayList<Secouriste> AssignmentRescuersGreedy(DPS dps) throws Exception {

        if (dps == null) {
            throw new IllegalArgumentException("L'argument est null");
        }

        ArrayList<Competence> competencesBesoins = this.besoinManagement.getBesoinByDPS(dps).getCompetences();
        System.out.println("Competences: " + competencesBesoins.size());
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
                        if (competencesBesoins.get(i).getIntitule().equals(this.competences.get(indiceComp))) {
                            competencesBesoins.remove(i);
                            break;
                        }
                    }
                } else {
                    secouristesAssignement.add(secouristeSelect);
                    retirerSecouristeComp(secouristeSelect, competencesBesoins, tabSecouComp, competenceSelect);

                    Affectation affectation = new Affectation(secouristeSelect, dps, competenceSelect);
                    if (!this.affectationManagement.isExist(affectation)) {
                        this.affectationManagement.addAffectation(affectation);
                    }
                    this.besoinManagement.deleteBesoinByDPSAndCompetence(dps, competenceSelect);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return secouristesAssignement;
    }

    /**
     * Permettant de récupérer tout les secouristes disponibles
     * @param journee - A day
     * @return rescuer list available
     */
    private List<Secouriste> secouristesDisponible(Journee journee) {
        List<Secouriste> secouristesJour = this.secouristeManagement.findByIdJournee(this.journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee()));
        List<Secouriste> ret = new ArrayList<>();
        for (Secouriste secouriste : secouristesJour) {
            long idJournee = this.journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee());
            long idSecouriste = secouriste.getIdSecouriste();
            if (!this.affectationManagement.rescuerAvailable(idJournee, idSecouriste)) {
                ret.add(secouriste);
            }
        }
        return ret;
    }

    /**
     * Permettant d'avoi un tableau avec comme 1ère colonne : l'id du secouriste et les autres : les compétences
     * Il y a 1 quand le secouriste à la compétence et 0 si non
     * @param secouristes - rescuer
     * @return tab of rescuer with skills
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

            for (String compentence : competences) {
                if (compSecouriste.contains(compentence)) {
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
     * On récupère l'indice de la competence avec le moins de secouristes assignés dans l'attribut competence
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
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
     * Permet de créer un tableau avec pour chaque indices de compétences
     * le nombre d'occurences qu'il apparait chez les secouristes
     * @param tabSecouComp
     * @return
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
     * Sélectionne les secouristes qui ont la compétence la moins présente
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
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
     * Permet de Récupéré le secouriste qui a le moins de compétence dans les secouristes séléctionnés
     * @param tabSecouComp
     * @param competencesUtiles
     * @return
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
     * Permet après avoir trouvé le secouriste de le supprimer de la liste et d'enlever
     * la competence utile qui a été attribué
     * @param secouriste
     * @param competencesUtiles
     * @param tabSecouComp
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
