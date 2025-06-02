package model.dao;

import java.sql.*;
import java.util.*;
import model.data.persistence.Secouriste;

public class SecouristeDAO {

    public List<Secouriste> findAll() {
        List<Secouriste> secouristes = new ArrayList<>();
        try (Connection con = ConnexionBDD.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM secouriste")) {

            while (rs.next()) {
                Secouriste s = new Secouriste(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("email"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
                secouristes.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return secouristes;
    }
}