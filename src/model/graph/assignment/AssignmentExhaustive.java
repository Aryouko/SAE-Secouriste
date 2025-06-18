package model.graph.assignment;

import model.dao.CompetenceDAO;
import model.data.persistence.*;
import model.data.service.*;
import model.graph.utils.MatrixUtils;

import java.util.*;

import static model.dao.DAOFactory.NecessiteDAO;

/**
 * Nouvelle version de AssignmentExhaustive en s'inspirant de AssignmentGreedy.
 * Effectue une affectation exhaustive des secouristes aux competences d'un DPS,
 * tout en tirant profit des services pour l'abstraction des DAO.
 */
public class AssignmentExhaustive {

    private final MatrixUtils matrixUtils = new MatrixUtils();
    private final PossessionManagement possessionManagement = new PossessionManagement();
    private final SecouristeManagement secouristeManagement = new SecouristeManagement();
    private final JourneeManagement journeeManagement = new JourneeManagement();
    private final AffectationManagement affectationManagement = new AffectationManagement();
    private final BesoinManagement besoinManagement = new BesoinManagement();

    private Map<Competence, List<Competence>> dependencies;
    private HashMap<Competence, Secouriste> bestAffectation;
    private int maxAssigned;
    private HashMap<Competence, Secouriste> affectation;

    /**
     * Assigns rescuers to a DPS (First Aid Post) using an exhaustive approach.
     *
     * @param dps - a DPS (First Aid Post)
     */
    public void assignmentRescuersExhaustive(DPS dps) {
        if (dps == null) {
            throw new IllegalArgumentException("DPS null");
        }

        // Initialisation des variables d'instance
        this.bestAffectation = new HashMap<>();
        this.maxAssigned = 0;
        this.affectation = new HashMap<>();

        initDependencies();
        List<Competence> competences = besoinManagement.getBesoinByDPS(dps).getCompetences();
        if (competences == null || competences.isEmpty()) {
            throw new IllegalArgumentException("Aucune compétence demandée");
        }

        List<Secouriste> secouristes = secouristesDisponible(dps.getJournee());
        if (secouristes.isEmpty()) {
            throw new IllegalArgumentException("Aucun secouriste disponible");
        }

        if (!backtrack(0, competences, secouristes, new HashMap<>(), new HashSet<>())) {
            if (bestAffectation.isEmpty()) {
                throw new IllegalStateException("Aucune affectation possible");
            } else {
                affectation.clear();
                affectation.putAll(bestAffectation);
            }
        }

        // Sauvegarde des affectations trouvées
        for (Map.Entry<Competence, Secouriste> entry : affectation.entrySet()) {
            Affectation a = new Affectation(entry.getValue(), dps, entry.getKey());
            if (!affectationManagement.isExist(a)) {
                affectationManagement.addAffectation(a);
                besoinManagement.deleteBesoinByDPSAndCompetence(dps, entry.getKey());
            }
        }

        // Retourner la liste des secouristes assignés
        ArrayList<Secouriste> secouristesAssignes = new ArrayList<>();
        for (Secouriste secouriste : affectation.values()) {
            if (!secouristesAssignes.contains(secouriste)) {
                secouristesAssignes.add(secouriste);
            }
        }

    }

    private void initDependencies() {
        List<Competence> competences = new CompetenceDAO().findAll();
        List<Necessite> necessites = NecessiteDAO.findAll();
        this.dependencies = matrixUtils.buildAllSuperiorDependencies(competences, necessites);
    }

    private boolean backtrack(int index, List<Competence> competences, List<Secouriste> secouristes,
                              HashMap<Competence, Secouriste> current, HashSet<Secouriste> used) {

        if (index == competences.size()) {
            if (current.size() > maxAssigned) {
                bestAffectation.clear();
                bestAffectation.putAll(current);
                maxAssigned = current.size();
            }
            return true;
        }

        boolean found = false;
        Competence comp = competences.get(index);

        for (Secouriste s : secouristes) {
            if (!used.contains(s)) {
                List<Competence> competencesSec = possessionManagement.getPossessionBySecouriste(s).getCompetencesSec();
                if (possede(competencesSec, comp)) {
                    current.put(comp, s);
                    used.add(s);
                    found |= backtrack(index + 1, competences, secouristes, current, used);
                    current.remove(comp);
                    used.remove(s);
                }
            }
        }

        // Tenter sans assigner cette compétence
        found |= backtrack(index + 1, competences, secouristes, current, used);

        return found;
    }

    private boolean possede(List<Competence> competencesSec, Competence cible) {
        if (competencesSec.contains(cible)) return true;
        List<Competence> sup = dependencies.getOrDefault(cible, new ArrayList<>());
        for (Competence s : sup) {
            if (possede(competencesSec, s)) return true;
        }
        return false;
    }

    private List<Secouriste> secouristesDisponible(Journee journee) {
        long idJournee = journeeManagement.getJourneeByJour(journee.getJour(), journee.getMois(), journee.getAnnee());
        List<Secouriste> secouristes = secouristeManagement.findByIdJournee(idJournee);
        List<Secouriste> disponibles = new ArrayList<>();
        for (Secouriste s : secouristes) {
            if (affectationManagement.rescuerAvailable(idJournee, s.getIdSecouriste())) {
                disponibles.add(s);
            }
        }
        return disponibles;
    }

    public HashMap<Competence, Secouriste> getAffectation() {
        return affectation;
    }
}