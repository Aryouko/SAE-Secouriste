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


    /**
     * findById find the secouriste by his id
     *
     * @return return the secouriste
     */
    public Secouriste findById(long idSecouriste) {
        String query = "SELECT * FROM Secouriste WHERE Secouriste.idSecouriste = ?";
        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idSecouriste);
            ResultSet rs = stmt.executeQuery();
            Secouriste secouriste = new Secouriste(
                    rs.getLong("idSecouriste"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("tel"),
                    rs.getString("adresse")
            );
            return secouriste;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * updateSecouriste is to update like in the register controller
     *
     * @param secouriste a secouriste
     * @return a boolean that verify the right fonctionnement.
     */
    public boolean updateSecouriste(Secouriste secouriste) {
        String query = "UPDATE Secouriste SET nom = ?, prenom = ?, date_naissance = ?, tel = ?, adresse = ? WHERE idSecouriste = ?";
        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, secouriste.getNom());
            stmt.setString(2, secouriste.getPrenom());
            stmt.setString(3, secouriste.getDateNaissance());
            stmt.setString(4, secouriste.getTel());
            stmt.setString(5, secouriste.getAdresse());
            stmt.setLong(6, secouriste.getIdSecouriste());

            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * addSecouriste is to add a secouriste
     *
     * @param secouriste a secouriste
     * @return a boolean that verify the right fonctionnement.
     */
    public boolean addSecouriste(Secouriste secouriste) {
        String query = "INSERT INTO Secouriste (idSecouriste, nom, prenom, date_naissance, tel, adresse) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, secouriste.getIdSecouriste());
            stmt.setString(2, secouriste.getNom());
            stmt.setString(3, secouriste.getPrenom());
            stmt.setString(4, secouriste.getDateNaissance());
            stmt.setString(5, secouriste.getTel());
            stmt.setString(6, secouriste.getAdresse());

            int inserted = stmt.executeUpdate();
            return inserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}