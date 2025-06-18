package model.data.service;
import model.dao.AffectationDAO;
import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.persistence.Secouriste;
import model.graph.assignment.AssignmentExhaustive;
import model.graph.assignment.AssignmentGreedy;
import model.utils.Settings;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static model.data.service.AuthentificationManagement.getInstanceAuthentificationManagement;
import static model.data.service.SecouristeManagement.getInstanceSecouristeManagement;
import static model.utils.Settings.useGreedy;

/**
 * Class allow to collect all affectation
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class AffectationManagement {

    /**
     * Singleton instance of AffectationManagement.
     */
    private final AffectationDAO affectationDAO = new AffectationDAO();


    /**
     * Get the list of rescuers assigned to a specific DPS.
     *
     * @param idDps The ID of the DPS for which to retrieve assigned rescuers.
     * @return A list of Secouriste objects representing the rescuers assigned to the specified DPS.
     */
    public List<Integer> getIdRescuersByDps(long idDps) {
        return this.affectationDAO.findIdRescuerByDPS(idDps);
    }

    /**
     * Checks if a rescuer is available for a specific day.
     *
     * @param idDay The ID of the day to check.
     * @param idRescuer The ID of the rescuer to check.
     * @return true if the rescuer is available, false otherwise.
     */
    public boolean rescuerAvailable(long idDay, long idRescuer) {
        return !this.affectationDAO.rescuerThisDay(idDay, idRescuer);
    }

    /**
     * To check if an affectation already exists in the database.
     *
     * @param affectation The affectation to check for existence.
     * @return true if the affectation exists, false otherwise.
     */
    public boolean isExist(Affectation affectation) {
        return this.affectationDAO.exists(affectation);
    }

    /**
     * Adds a new affectation to the database.
     */
    public void addAffectation(Affectation affectation) {
        this.affectationDAO.insert(affectation);
    }

    /**
     * Retrieves all affectations for a given rescuer.
     *
     * @param secouriste The rescuer for whom to retrieve affectations.
     * @return A list of Affectation objects associated with the specified rescuer.
     */
    public List<Affectation> getAffectationsByRescuer(Secouriste secouriste) {
        return this.affectationDAO.findByRescuer(secouriste.getIdSecouriste());
    }

    /**
     * Retrieves all affectations for a given DPS.
     *
     * @param dps The DPS for which to retrieve affectations.
     * @return A list of Affectation objects associated with the specified DPS.
     */
    public List<Affectation> getAffectationsByDps(DPS dps) {
        return this.affectationDAO.findByDPS(dps.getId());
    }

    /**
     * Launches a greedy assignment of rescuers to a DPS.
     * This method uses a greedy algorithm to assign rescuers based on their competencies.
     *
     * @param dps The DPS for which the assignment is to be made.
     */
    public void launchAffectation(DPS dps) {
        if (useGreedy()) {
            new AssignmentGreedy().AssignmentRescuersGreedy(dps);
        } else {
            new AssignmentExhaustive().AssignmentRescuersExhaustive(dps);
        }
    }

    /**
     * Removes an affectation from the database.
     *
     * @param affectation The affectation to remove.
     */
    public void removeAffectation(Affectation affectation) {
        this.affectationDAO.delete(affectation);
    }


    /**
     * Exports the affectations of rescuers to a CSV file.
     * If the user is an admin, it exports all assignments.
     * Otherwise, it exports only the assignments of the specified rescuer.
     *
     * @param idRescuer The ID of the rescuer for whom to export assignments.
     */
    public void exportYourAffectationToCSV(long idRescuer) {
        if (getInstanceAuthentificationManagement().isAdmin()) {
            List<Secouriste> secouristes = getInstanceSecouristeManagement().findAll();
            String fileName = "Toutes_les_affectations.csv";
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("IdSecouriste;Nom;Prenom;IdDPS;NomDPS;Competence\n");
                int total = 0;
                for (Secouriste secouriste : secouristes) {
                    List<Affectation> affectations = this.affectationDAO.findByRescuer(secouriste.getIdSecouriste());
                    for (Affectation aff : affectations) {
                        Secouriste s = aff.getSecouristeAffect();
                        String nom = s != null ? s.getNom() : "";
                        String prenom = s != null ? s.getPrenom() : "";
                        String competence = aff.getCompetenceAffect() != null ? aff.getCompetenceAffect().getIntitule() : "";
                        String nomDps = aff.getDPSAffect() != null ? aff.getDPSAffect().getName() : "";
                        writer.write(
                            aff.getSecouristeAffect().getIdSecouriste() + ";" +
                            nom + ";" +
                            prenom + ";" +
                            aff.getDPSAffect().getId() + ";" +
                            nomDps + ";" +
                            competence + "\n"
                        );
                        total++;
                    }
                }
                writer.flush();
                // System.out.println("Nombre total d'affectations exportées : " + total);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'exportation des affectations : " + e.getMessage());
            }
        } else {
            List<Affectation> affectations = this.affectationDAO.findByRescuer(idRescuer);
            String fileName = "Vos_affectations.csv";
            try (FileWriter writer = new FileWriter(fileName)) {
                System.out.println("Export CSV lancé, nb affectations = " + affectations.size());
                writer.write("IdSecouriste;Nom;Prenom;IdDPS;NomDPS;Competence\n");
                for (Affectation aff : affectations) {
                    Secouriste s = aff.getSecouristeAffect();
                    String nom = s != null ? s.getNom() : "";
                    String prenom = s != null ? s.getPrenom() : "";
                    String competence = aff.getCompetenceAffect() != null ? aff.getCompetenceAffect().getIntitule() : "";
                    String nomDps = aff.getDPSAffect() != null ? aff.getDPSAffect().getName() : "";
                    writer.write(
                        aff.getSecouristeAffect().getIdSecouriste() + ";" +
                        nom + ";" +
                        prenom + ";" +
                        aff.getDPSAffect().getId() + ";" +
                        nomDps + ";" +
                        competence + "\n"
                    );
                }
                writer.flush();
            } catch (IOException e) {
                System.err.println("Erreur lors de l'exportation des affectations : " + e.getMessage());
            }
        }
    }
}
