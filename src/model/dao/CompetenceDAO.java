package model.dao;

import model.data.persistence.Competence;
import model.data.persistence.Secouriste;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

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
}
