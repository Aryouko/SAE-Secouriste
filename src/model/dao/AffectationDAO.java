package model.dao;

import model.data.persistence.Affectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AffectationDAO {

    public void insert(Affectation affectation) {
        String query = "INSERT INTO Affectation VALUES (?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, affectation.getSecouristeAffect().getIdSecouriste());
            stmt.setLong(2, affectation.getDPSAffect().getId());
            stmt.setString(3, affectation.getCompetenceAffect().getIntitule());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean rescuerThisDay(long idJournee, long idSecouriste) {
        String query = "SELECT id FROM Affectation JOIN DPS ON Affectation.DPSAffect = DPS.id WHERE journee = ? AND secouristeAffect = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idJournee);
            stmt.setLong(2, idSecouriste);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false; // Pas trouvé
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
