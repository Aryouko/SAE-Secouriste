package model.dao;

import model.data.persistence.Affectation;
import model.data.persistence.Secouriste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        boolean ret = false;
        String query = "SELECT 1 FROM Affectation a JOIN DPS d ON a.DPSAffect = d.id WHERE d.journee = ? AND a.secouristeAffect = ? LIMIT 1";
        System.out.println("Query check: journee=" + idJournee + ", secouriste=" + idSecouriste);

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idJournee);
            stmt.setLong(2, idSecouriste);

            try (ResultSet rs = stmt.executeQuery()) {
                ret = rs.next(); // true si déjà affecté ce jour
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = true;
        }
        return ret;
    }

    public boolean exists(Affectation affectation) {
        String query = "SELECT 1 FROM affectation WHERE secouristeAffect = ? AND DPSAffect = ? AND competenceAffect = ?";
        boolean ret = false;

        try (Connection con = ConnectionBDD.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, affectation.getSecouristeAffect().getIdSecouriste());
            stmt.setLong(2, affectation.getDPSAffect().getId());
            stmt.setString(3, affectation.getCompetenceAffect().getIntitule());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Integer> findIdRescuerByDPS(long idDPS) {
        List<Integer> idSecouristes = new ArrayList<>();
        String query = "SELECT secouristeAffect FROM Affectation WHERE DPSAffect = ?";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idDPS);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                idSecouristes.add(rs.getInt("secouristeAffect"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idSecouristes;
    }
}
