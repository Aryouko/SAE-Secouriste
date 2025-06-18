package model.graph.assignment;

import model.dao.*;
import model.data.persistence.*;
import model.data.service.*;
import model.graph.utils.MatrixUtils;

import java.util.*;

/**
 * Classe AssignmentExhaustive - Réalise une affectation exhaustive des secouristes aux compétences requises pour un DPS.
 */
public class AssignmentExhaustive {

    private final Map<Competence, Secouriste> affectation = new HashMap<>();
    private final Map<Competence, Secouriste> bestAffectation = new HashMap<>();
    private Map<Competence, List<Competence>> dependencies;
    private int maxAssigned = 0;

    private final MatrixUtils matrixUtils = new MatrixUtils();
    private final BesoinManagement besoinManagement = new BesoinManagement();
    private final AffectationManagement affectationManagement = new AffectationManagement();
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private final JourneeManagement journeeManagement = new JourneeManagement();
    private final PossessionManagement possessionManagement = new PossessionManagement();
    private final NecessiteDAO necessiteDAO = new NecessiteDAO();

    /**
     * Effectue l'affectation exhaustive.
     * @param dps - Le DPS ciblé.
     */
    public void AssignmentRescuersExhaustive(DPS dps) {
        if (dps == null) throw new IllegalArgumentException("DPS null");

        List<Competence> competences = besoinManagement.getBesoinByDPS(dps).getCompetences();
        List<Necessite> necessites = necessiteDAO.findAll();
        initDependencies(competences, necessites);

        Journee journee = dps.getJournee();
        List<Secouriste> secouristes = secouristesDisponible(journee);
        if (secouristes.isEmpty()) throw new IllegalArgumentException("Aucun secouriste disponible");

        HashSet<Secouriste> dejaAffectes = new HashSet<>();

        boolean success = backtrack(0, competences, secouristes, affectation, dejaAffectes);

        Map<Competence, Secouriste> solution = success ? affectation : bestAffectation;
        for (Map.Entry<Competence, Secouriste> entry : solution.entrySet()) {
            Affectation a = new Affectation(entry.getValue(), dps, entry.getKey());
            if (!affectationManagement.isExist(a)) {
                affectationManagement.addAffectation(a);
                besoinManagement.deleteBesoinByDPSAndCompetence(dps, entry.getKey());
            }
        }
    }

    private void initDependencies(List<Competence> competences, List<Necessite> necessites) {
        dependencies = matrixUtils.buildAllSuperiorDependencies(competences, necessites);
    }

    private boolean backtrack(int index, List<Competence> competences, List<Secouriste> secouristes,
                              Map<Competence, Secouriste> affectationActuelle, Set<Secouriste> dejaAffectes) {
        if (index == competences.size()) {
            if (affectationActuelle.size() > maxAssigned) {
                bestAffectation.clear();
                bestAffectation.putAll(affectationActuelle);
                maxAssigned = affectationActuelle.size();
            }
            return true;
        }

        boolean found = false;
        Competence competence = competences.get(index);

        for (Secouriste s : secouristes) {
            if (dejaAffectes.contains(s)) continue;
            List<Competence> compSec = possessionManagement.getPossessionBySecouriste(s).getCompetencesSec();
            if (possede(compSec, competence)) {
                affectationActuelle.put(competence, s);
                dejaAffectes.add(s);

                found |= backtrack(index + 1, competences, secouristes, affectationActuelle, dejaAffectes);

                affectationActuelle.remove(competence);
                dejaAffectes.remove(s);
            }
        }

        found |= backtrack(index + 1, competences, secouristes, affectationActuelle, dejaAffectes);
        return found;
    }

    private boolean possede(List<Competence> competencesSec, Competence cible) {
        if (competencesSec.contains(cible)) return true;
        List<Competence> sup = dependencies.getOrDefault(cible, new ArrayList<>());
        for (Competence c : sup) {
            if (possede(competencesSec, c)) return true;
        }
        return false;
    }

    private List<Secouriste> secouristesDisponible(Journee journee) {
        long idJournee = journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee());
        List<Secouriste> secouristesJour = secouristeManagement.findByIdJournee(idJournee);
        List<Secouriste> disponibles = new ArrayList<>();

        for (Secouriste s : secouristesJour) {
            if (affectationManagement.rescuerAvailable(idJournee, s.getIdSecouriste())) {
                disponibles.add(s);
            }
        }
        return disponibles;
    }

    public Map<Competence, Secouriste> getAffectation() {
        return new HashMap<>(affectation);
    }
}