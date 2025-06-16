package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import javafx.scene.image.Image;
import model.data.persistence.Secouriste;

public class SecouristeDAO {

    public List<Secouriste> findAll() {
        List<Secouriste> secouristes = new ArrayList<>();

        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Secouriste")) {

            while (rs.next()) {
                Secouriste s = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("tel"),
                        rs.getString("adresse"),
                        rs.getBytes("photo")
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
        String query = "SELECT * FROM Secouriste s JOIN Disponibilite d ON s.idSecouriste = d.secouristeDisp WHERE journeeDisp = ? ";

        try (Connection con = ConnectionBDD.getConnection();
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
                        rs.getString("adresse"),
                        rs.getBytes("photo")
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
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idSecouriste);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Secouriste secouriste = new Secouriste(
                        rs.getLong("idSecouriste"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("tel"),
                        rs.getString("adresse"),
                        rs.getBytes("photo")
                );
                return secouriste;
            }
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
        try (Connection con = ConnectionBDD.getConnection();
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
        String query = "INSERT INTO Secouriste (idSecouriste, nom, prenom, date_naissance, adresse, tel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, secouriste.getIdSecouriste());
            stmt.setString(2, secouriste.getNom());
            stmt.setString(3, secouriste.getPrenom());
            stmt.setString(4, secouriste.getDateNaissance());
            stmt.setString(5, secouriste.getAdresse());
            stmt.setString(6, secouriste.getTel());


            int inserted = stmt.executeUpdate();
            return inserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * insérer une photo pour un secouriste existant
     *
     * @param id - id du secouriste
     * @param imageFile fichier image à insérer
     * @return boolean indiquant si l'insertion a réussi
     */
    public boolean insererPhoto(long id, File imageFile) {
        String query = "UPDATE Secouriste SET photo = ? WHERE idSecouriste = ?";

        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             FileInputStream fis = new FileInputStream(imageFile)) {

            stmt.setBinaryStream(1, fis, (int) imageFile.length());
            stmt.setLong(2, id);

            int updated = stmt.executeUpdate();
            return updated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * recuperer une photo pour un secouriste existant
     *
     * @param id - id du secouriste
     * @return la photo de profil
     */
    public Image recupererPhoto(long id) {
        String sql = "SELECT photo FROM Secouriste WHERE idSecouriste = ?";
        try (Connection conn = ConnectionBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("photo");
                if (is != null) {
                    return new Image(is);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(Secouriste secouriste) {
        System.out.println("ID à supprimer : " + secouriste.getIdSecouriste());
        String query = "DELETE FROM Secouriste WHERE idSecouriste = ?";
        try (Connection con = ConnectionBDD.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setLong(1, secouriste.getIdSecouriste());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}