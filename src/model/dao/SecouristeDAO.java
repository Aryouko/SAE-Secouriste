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

    /**
     * findByDay return all the secouriste ready for a specific day
     *
     * @return
     */
    public List<Secouriste> findByDay(long idJourFind) {
        List<Secouriste> secouristes = new ArrayList<>();
        String query = "SELECT * FROM secouriste JOIN disponibilite ON secouriste.idSecouriste = disponibilite.idDisponibilite WHERE disponibilite.jour = ?";


        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query);) {

            stmt.setLong(1, idJourFind);  // on remplace le ? par la valeur du login
            ResultSet rs = stmt.executeQuery();

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