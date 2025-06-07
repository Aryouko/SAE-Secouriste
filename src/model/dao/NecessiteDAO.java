package model.dao;

import java.sql.*;
import java.util.*;

import model.data.persistence.Competence;
import model.data.persistence.Necessite;

import static model.dao.ConnectionBDD.getConnection;

public class NecessiteDAO {

    public static List<Necessite> findAll() {

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

        }
        return null;
    }

    public static void addSkillNeeded() {

    }
}
