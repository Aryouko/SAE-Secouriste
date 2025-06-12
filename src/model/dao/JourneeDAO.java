package model.dao;

import model.data.persistence.Journee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JourneeDAO {

    public void insert(Journee journee) {
        String query = "INSERT INTO Journee (jour, mois, annee) VALUES (?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, journee.getJour());
            stmt.setLong(2, journee.getMois());
            stmt.setLong(3, journee.getAnnee());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long findIdByJour(int jour, int mois, int annee) {
        String query = "SELECT id FROM Journee WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, jour);
            stmt.setInt(2, mois);
            stmt.setInt(3, annee);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            } else {
                return null; // Pas trouvé
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
