package model.graph;

import model.dao.*;
import model.data.persistence.*;

import java.util.*;

/**
 * Classe AssignmentExhaustive - Cette classe réalise une affectation exhaustive des secouristes
 * aux compétences demandées dans un DPS donné, en tenant compte des compétences supérieures.
 */
public class AssignmentExhaustive {

    /**
     * Map qui associe chaque compétence à un secouriste affecté.
     */
    private final HashMap<Competence, Secouriste> affectation;

    private final MatrixUtils matrixUtils = new MatrixUtils();


    /**
     * Dépendances entre compétences, indiquant quelles compétences sont supérieures à d'autres.
     */
    private Map<Competence, List<Competence>> dependencies;

    /**
     * Initialise les dépendances entre compétences (compétences "supérieures" pour chaque compétence).
     */
    private void initDependencies() {
        List<Necessite> necessites = new NecessiteDAO().findAll();
        List<Competence> competences = new CompetenceDAO().findAll();

        this.dependencies = matrixUtils.buildAllSuperiorDependencies(competences, necessites);
    }

    /**
     * Constructeur AssignmentExhaustive qui effectue l'affectation des secouristes aux compétences du DPS.
     * @param dps - Le DPS à traiter (doit être non null)
     * @param competences - Liste des compétences à affecter (doit être non null)
     * @throws IllegalArgumentException si les arguments sont null ou si aucun secouriste disponible
     * @throws IllegalStateException si aucune affectation possible n'a pu être trouvée
     */
    public AssignmentExhaustive(DPS dps, ArrayList<Competence> competences) {
        this.affectation = new HashMap<>();
        initDependencies();

        if (dps == null || competences == null) {
            throw new IllegalArgumentException("Arguments null");
        }

        Journee journee = dps.getJournee();
        List<Secouriste> secouristes = secouristesDisponible(journee);
        if (secouristes.isEmpty()) {
            throw new IllegalArgumentException("Aucun secouriste disponible");
        }

        HashSet<Secouriste> dejaAffectes = new HashSet<>();

        boolean success = backtrack(0, competences, secouristes, affectation, dejaAffectes);

        if (!success) {
            throw new IllegalStateException("Aucune affectation possible");
        }
    }

    /**
     * Algorithme de backtracking pour affecter récursivement chaque compétence à un secouriste disponible.
     * @param index - Index courant dans la liste des compétences à affecter
     * @param competences - Liste des compétences à affecter
     * @param secouristes - Liste des secouristes disponibles
     * @param affectationActuelle - Affectation en cours des compétences aux secouristes
     * @param dejaAffectes - Ensemble des secouristes déjà affectés
     * @return true si une affectation complète est trouvée, false sinon
     */
    private boolean backtrack(int index, List<Competence> competences, List<Secouriste> secouristes, HashMap<Competence, Secouriste> affectationActuelle, HashSet<Secouriste> dejaAffectes) {

        if (index == competences.size()) {
            return true; // toutes les compétences ont été assignées
        }

        Competence competence = competences.get(index);

        for (Secouriste s : secouristes) {
            if (!dejaAffectes.contains(s)) {

                List<Competence> competencesDuSecouriste = new PossessionDAO().find(s).getCompetencesSec();
                if (possede(competencesDuSecouriste, competence)) {
                    affectationActuelle.put(competence, s);
                    dejaAffectes.add(s);

                    if (backtrack(index + 1, competences, secouristes, affectationActuelle, dejaAffectes)) {
                        return true;
                    }

                    // backtrack
                    affectationActuelle.remove(competence);
                    dejaAffectes.remove(s);
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un secouriste possède une compétence donnée ou une compétence supérieure.
     * @param competencesSec - Liste des compétences du secouriste
     * @param cible - Compétence ciblée à vérifier
     * @return true si la compétence ciblée ou une compétence supérieure est possédée, false sinon
     */
    private boolean possede(List<Competence> competencesSec, Competence cible) {
        // Si le secouriste possède exactement la compétence ciblée
        if (competencesSec.contains(cible)) {
            return true;
        }

        // Sinon, on regarde si le secouriste possède une compétence supérieure
        List<Competence> compSup = dependencies.get(cible);
        if (compSup != null) {
            for (Competence sup : compSup) {
                // Recursion : si la compétence supérieure elle-même a des superclasses
                if (possede(competencesSec, sup)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retourne la map actuelle des affectations compétences -> secouristes.
     * @return la map des affectations
     */
    public HashMap<Competence, Secouriste> getAffectation() {
        return affectation;
    }


    /**
     * Récupère la liste des secouristes disponibles pour une journée donnée, c’est-à-dire ceux
     * qui ne sont pas déjà affectés ce jour-là.
     * @param journee - la journée concernée
     * @return la liste des secouristes disponibles
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
     * Crée une instance de Competence à partir d’un nom.
     * @param nom - Nom de la compétence
     * @return L’objet Competence correspondant
     */
    private Competence getCompetence(String nom) {
        return new Competence(nom);
    }

}
