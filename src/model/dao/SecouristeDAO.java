package model.dao;

import java.sql.*;
import java.util.*;
import model.data.persistence.Secouriste;

public class SecouristeDAO {

    public List<Secouriste> findAll() {
        List<Secouriste> secouristes = new ArrayList<>();

        try (Connection con = ConnexionBDD.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Secouriste")) {

            while (rs.next()) {
                Secouriste s = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
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

    public Secouriste findSecouriste(long id) {
        Secouriste secouriste = null;

        try (Connection con = ConnexionBDD.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Secouriste WHERE idSecouriste =" + id)) {

            while (rs.next()) {
                secouriste = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return secouriste;
    }

    /**
     * findByDay retourne tous les secouristes disponibles pour un jour donné
     */
    public List<Secouriste> findByDay(long idJourFind) {
        List<Secouriste> secouristes = new ArrayList<>();
        String query = "SELECT * FROM Secouriste " +
                "JOIN Disponibilite ON Secouriste.idSecouriste = Disponibilite.secouristeDisp " +
                "WHERE Disponibilite.journeeDisp = ?";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idJourFind);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Secouriste s = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
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