package model.dao;

import java.sql.*;
import java.util.*;

import model.data.persistence.Competence;
import model.data.persistence.Necessite;

import static model.dao.ConnectionBDD.getConnection;

public class NecessiteDAO {

    /**
     * This method retrieves all Necessite objects from the database.
     *
     * @return List<Necessite> a list of all Necessite objects
     */
    public List<Necessite> findAll() {
        List<Necessite> listSkillNeeded = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Necessite")) {

            while(rs.next()) {
                Competence comp1 = new Competence(rs.getString("comp1"));
                Competence comp2 = new Competence(rs.getString("comp2"));

                Necessite skillNeeded = new Necessite(comp1, comp2);
                listSkillNeeded.add(skillNeeded);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listSkillNeeded;
    }
}
