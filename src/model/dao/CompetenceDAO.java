package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import model.data.persistence.Competence;

import static model.dao.ConnectionBDD.getConnection;

public class CompetenceDAO {

    public List<String> findAllIntitule() {
        List<String> competences = new ArrayList<>();

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Competence")) {

            while (rs.next()) {
                String intitule = rs.getString("intitule");
                competences.add(intitule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competences;
    }

    public List<Competence> findAll() {
        List<Competence> competences = new ArrayList<>();

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Competence")) {

            while (rs.next()) {
                Competence competence = new Competence(rs.getString("intitule"));
                competences.add(competence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competences;
    }
}
