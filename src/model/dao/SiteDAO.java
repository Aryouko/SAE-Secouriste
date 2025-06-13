package model.dao;


import model.data.persistence.Site;
import model.data.persistence.Sport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO {

    public List<Site> findAll() {
        List<Site> sites = new ArrayList<>();

        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Site")) {

            while (rs.next()) {
                Site s = new Site(
                        rs.getLong("CODE"),
                        rs.getString("NOM"),
                        rs.getFloat("LONGITUDE"),
                        rs.getFloat("LATITUDE")
                );
                sites.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sites;
    }

    public Site findByName(String siteName) {
        Site ret = null;
        String query = "SELECT * FROM Site WHERE NOM = ? ";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, siteName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ret = new Site(rs.getLong("CODE"), siteName, rs.getLong("LONGITUDE"), rs.getLong("LATITUDE"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public Site findById(long id) {
        Site ret = null;
        String query = "SELECT * FROM Site WHERE ID = ? ";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ret = new Site(id, rs.getString("NOM"), rs.getLong("LONGITUDE"), rs.getLong("LATITUDE"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
