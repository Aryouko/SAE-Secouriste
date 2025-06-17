package model.dao;

import javafx.scene.image.Image;
import model.data.persistence.Administrateur;
import model.data.persistence.Administrateur;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.dao.ConnectionBDD.getConnection;

public class AdministrateurDAO {

    public void insert(Administrateur administrateur) {
        String query = "INSERT INTO Administrateur (idAdministrateur, nom, prenom, date_naissance, adresse, tel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, administrateur.getIdAdministrateur());
            stmt.setString(2, administrateur.getNom());
            stmt.setString(3, administrateur.getPrenom());
            stmt.setString(4, administrateur.getDateNaissance());
            stmt.setString(5, administrateur.getAdresse());
            stmt.setString(6, administrateur.getTel());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Administrateur> findAll() {
        List<Administrateur> administrateurs = new ArrayList<>();

        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Administrateur")) {

            while (rs.next()) {
                Administrateur s = new Administrateur(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
                administrateurs.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrateurs;
    }

    /**
     * findById find the adminstrator by his id
     *
     * @return return the administrator
     */
    public Administrateur findById(long idAdministrateur) {
        String query = "SELECT * FROM Administrateur WHERE Administrateur.idAdministrateur = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idAdministrateur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Administrateur administrateur = new Administrateur(
                        rs.getLong("idAdministrateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
                return administrateur;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insererPhoto(long idAdministrateur, byte[] photoBytes) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Administrateur SET photo = ? WHERE idAdministrateur = ?")) {

            stmt.setBytes(1, photoBytes);
            stmt.setLong(2, idAdministrateur);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * recuperer une photo pour un Administrateur existant
     *
     * @param id - id du Administrateur
     * @return la photo de profil
     */
    public Image recupererPhoto(long id) {
        String sql = "SELECT photo FROM Administrateur WHERE idAdministrateur = ?";
        try (Connection conn = getConnection();
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
}