package model.graph.assignment;


import model.data.persistence.DPS;
import model.data.persistence.Disponibilite;
import model.data.persistence.Journee;
import model.data.persistence.Secouriste;
import model.data.service.AffectationManagement;
import model.data.service.JourneeManagement;
import model.data.service.SecouristeManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssignmentEx {

    JourneeManagement journeeManagement = new JourneeManagement();
    SecouristeManagement secouristeManagement = new SecouristeManagement();
    AffectationManagement affectationManagement = new AffectationManagement();

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

/*
    void backtrack(DPS dps, List<Secouriste> secouristes, Map<DPS, Secouriste> affectations) {
        secouristesDisponible(dps, secouristes);
        long dpsIndex = dps.getId();

        if (dpsIndex == dpsList.size()) {
            // Cas terminal : tous les DPS ont un secouriste

            return;
        }

        DPS dps = dpsList.get(dpsIndex);

        for (Secouriste s : secouristesDispo) {
            if (s.disponibilites.contains(dps.getJournee())) {
                // Affecter ce secouriste à ce DPS
                affectations.put(dps, s);

                // Pour éviter les doublons si 1 secouriste = 1 DPS max
                List<Secouriste> restants = new ArrayList<>(secouristesDispo);
                restants.remove(s);

                // Appel récursif pour le DPS suivant
                backtrack(dpsIndex + 1, dpsList, restants, affectations);

                // Backtrack
                affectations.remove(dps);
            }
        }
    }

 */
}
