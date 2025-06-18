package model.dao;

import model.data.persistence.*;

import java.sql.*;
import java.util.ArrayList;

public class PossessionDAO {

    public void insert(Possession possession) {
        String query = "INSERT INTO Possession (competence, secouriste) VALUES (?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            for (Competence competence : possession.getCompetencesSec()) {
                stmt.setString(1, competence.getIntitule());
                stmt.setLong(2, possession.getSecouriste().getIdSecouriste());
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Possession find (Secouriste secouriste) {
        Possession ret = null;
        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Possession WHERE SECOURISTE = " + secouriste.getIdSecouriste() + "")) {
            ArrayList<Competence> competences = new ArrayList<>();
            while (rs.next()) {
                String intitule = rs.getString("Competence");

                competences.add(new Competence(intitule));
            }
            ret = new Possession(competences, secouriste);
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return ret;
    }

    public Possession findCompetencesBySecouriste (Secouriste secouriste) {
        Possession ret = null;
        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Possession WHERE SECOURISTE = " + secouriste.getIdSecouriste() + "")) {
            ArrayList<Competence> competences = new ArrayList<>();
            while (rs.next()) {
                String intitule = rs.getString("Competence");

                competences.add(new Competence(intitule));
            }
            ret = new Possession(competences, secouriste);
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return ret;
    }


    public void deletePossession(long idSecouriste, String intituleCompetence) {
        String query = "DELETE FROM Possession WHERE secouriste = ? AND competence = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idSecouriste);
            stmt.setString(2, intituleCompetence);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllPossessionsForSecouriste(long idSecouriste) {
        String query = "DELETE FROM Possession WHERE secouriste = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, idSecouriste);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
