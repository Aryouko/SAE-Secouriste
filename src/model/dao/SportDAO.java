package model.dao;


import model.data.persistence.Besoin;
import model.data.persistence.Competence;
import model.data.persistence.DPS;
import model.data.persistence.Sport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportDAO {

    public List<Sport> findAll() {
        List<Sport> sports = new ArrayList<>();

        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Sport")) {

            while (rs.next()) {
                Sport s = new Sport(
                        rs.getLong("CODE"),
                        rs.getString("NOM")
                );
                sports.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sports;
    }

    public Sport findByName(String sportName) {
        Sport ret = null;
        String query = "SELECT * FROM Sport WHERE NOM = ? ";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, sportName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ret = new Sport(rs.getLong("CODE"), sportName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public Sport findById(long id) {
        Sport ret = null;
        String query = "SELECT * FROM Sport WHERE ID = ? ";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ret = new Sport(id, rs.getString("NOM"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
