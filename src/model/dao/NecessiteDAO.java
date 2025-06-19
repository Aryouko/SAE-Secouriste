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

    public void deleteNecessite(Necessite necessite) {
        String query = "DELETE FROM Necessite WHERE COMP1 = ? AND COMP2 = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, necessite.getComp1().getIntitule());
            stmt.setString(2, necessite.getComp2().getIntitule());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCreate(Necessite necessite) {
        String query = "SELECT 1 FROM Necessite WHERE COMP1 = ? AND COMP2 = ?";
        boolean ret = false;
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, necessite.getComp1().getIntitule());
            stmt.setString(2, necessite.getComp2().getIntitule());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public void insertNecessite(Necessite newNecessite) {
        String query = "INSERT INTO Necessite (comp1, comp2) VALUES (?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, newNecessite.getComp1().getIntitule());
            stmt.setString(2, newNecessite.getComp2().getIntitule());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
