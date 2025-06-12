package model.dao;

import model.data.persistence.Disponibilite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DisponibiliteDAO {


    public boolean insert(Disponibilite disponibilite) {
        String query = "INSERT INTO Disponibilite (jour, mois, annee)VALUES (?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, disponibilite.getJourDisp().getJour());
            stmt.setLong(2, disponibilite.getJourDisp().getMois());
            stmt.setLong(3, disponibilite.getJourDisp().getAnnee());

            int inserted = stmt.executeUpdate();
            return inserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long findIdByJour(int jour, int mois, int annee) {
        String query = "SELECT id FROM Disponibilite WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, jour);
            stmt.setInt(2, mois);
            stmt.setInt(3, annee);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    return null; // Pas trouvé
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteByJour(int jour, int mois, int annee) {
        String query = "DELETE FROM Disponibilite WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, jour);
            stmt.setInt(2, mois);
            stmt.setInt(3, annee);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
