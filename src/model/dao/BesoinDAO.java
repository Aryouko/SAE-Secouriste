package model.dao;

import model.data.persistence.*;

import java.sql.*;
import java.util.ArrayList;

public class BesoinDAO {
    public void insert(Besoin besoin) {
        for (Competence competence : besoin.getCompetences()) {
            String query = "INSERT INTO Besoin (DPS, COMPETENCE) VALUES (?,?)";
            try (Connection con = ConnectionBDD.getConnection();
                 PreparedStatement stmt = con.prepareStatement(query)) {
                 stmt.setLong(1, besoin.getDps().getId());
                 stmt.setString(2, competence.getIntitule());
                 stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Besoin findByDPS(DPS dps) {
        Besoin ret = null;
        String query = "SELECT * FROM Besoin WHERE DPS = ? ";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, dps.getId());
            ResultSet rs = stmt.executeQuery();

            ArrayList<Competence> competences = new ArrayList<>();
            while (rs.next()) {
                String intitule = rs.getString("COMPETENCE");
                competences.add(new Competence(intitule));
            }
            ret = new Besoin(dps, competences);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public long findByDPSAndCompetence(DPS dps, Competence competence) {
        long ret = -1;
        String query = "SELECT ID FROM Besoin WHERE DPS = ? AND COMPETENCE = ? LIMIT 1";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, dps.getId());
            stmt.setString(2, competence.getIntitule());
            ResultSet rs = stmt.executeQuery();

            ArrayList<Competence> competences = new ArrayList<>();
            if (rs.next()) {
                ret = rs.getLong("ID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public void deleteByDPSAndCompetence(DPS dps, Competence competence) {
        long idBesoin = findByDPSAndCompetence(dps, competence);
        String query = "DELETE FROM Besoin WHERE ID = ?";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idBesoin);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteByDps(long idDps) {
        String query = "DELETE FROM Besoin WHERE DPS = ?";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idDps);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteBesoin(Besoin besoin) {
        for (Competence competence : besoin.getCompetences()) {
            String query = "DELETE FROM Besoin WHERE DPS = ? AND COMPETENCE = ? LIMIT 1";

            try (Connection con = ConnectionBDD.getConnection();
                 PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setLong(1, besoin.getDps().getId());
                stmt.setString(2, competence.getIntitule());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
