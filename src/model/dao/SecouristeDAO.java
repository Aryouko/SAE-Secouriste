package model.dao;

import java.sql.*;
import java.util.*;
import model.data.persistence.Secouriste;

public class SecouristeDAO {

    public static List<Secouriste> findAll() {
        List<Secouriste> liste = new ArrayList<>();
        String query = "SELECT * FROM Secouriste";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Secouriste s = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
                s.setPseudo(rs.getString("pseudo"));
                s.setBio(rs.getString("bio"));
                liste.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static Secouriste findById(long id) {
        Secouriste s = null;
        String query = "SELECT * FROM Secouriste WHERE idSecouriste = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    s = new Secouriste(
                            rs.getLong("idSecouriste"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("date_naissance"),
                            rs.getString("tel"),
                            rs.getString("adresse")
                    );
                    s.setPseudo(rs.getString("pseudo"));
                    s.setBio(rs.getString("bio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static List<Secouriste> findByDay(long jour) {
        List<Secouriste> liste = new ArrayList<>();
        String query = "SELECT * FROM Secouriste WHERE idSecouriste IN (SELECT idSecouriste FROM Affectation WHERE jour = ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, jour); // <-- correction ici
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Secouriste s = new Secouriste(
                            rs.getLong("idSecouriste"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("date_naissance"),
                            rs.getString("tel"),
                            rs.getString("adresse")
                    );
                    s.setPseudo(rs.getString("pseudo"));
                    s.setBio(rs.getString("bio"));
                    liste.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }


    public static boolean updateSecouriste(Secouriste s) {
        String query = "UPDATE Secouriste SET nom = ?, prenom = ?, date_naissance = ?, tel = ?, adresse = ?, pseudo = ?, bio = ? WHERE idSecouriste = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getPrenom());
            stmt.setString(3, s.getDateNaissance());
            stmt.setString(4, s.getTel());
            stmt.setString(5, s.getAdresse());
            stmt.setString(6, s.getPseudo());
            stmt.setString(7, s.getBio());
            stmt.setLong(8, s.getIdSecouriste());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addSecouriste(Secouriste s) {
        String query = "INSERT INTO Secouriste (nom, prenom, date_naissance, tel, adresse, pseudo, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getPrenom());
            stmt.setString(3, s.getDateNaissance());
            stmt.setString(4, s.getTel());
            stmt.setString(5, s.getAdresse());
            stmt.setString(6, s.getPseudo());
            stmt.setString(7, s.getBio());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
