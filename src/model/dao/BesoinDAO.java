package model.dao;

import model.data.persistence.*;

import java.sql.*;
import java.util.ArrayList;

public class BesoinDAO {
    public void insert(Besoin besoin) {
        for (Competence competence : besoin.getCompetences()) {
            String query = "INSERT INTO Besoin VALUES (" + besoin.getDps().getId() + ",'" + competence.getIntitule() + "')";
            try (Connection con = ConnectionBDD.getConnection();
                 Statement stmt = con.createStatement()) {
                 stmt.executeUpdate(query);
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
                System.out.println(rs.getString("DPS"));
                String intitule = rs.getString("COMPETENCE");
                System.out.println(intitule);
                competences.add(new Competence(intitule));
            }
            ret = new Besoin(dps, competences);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
