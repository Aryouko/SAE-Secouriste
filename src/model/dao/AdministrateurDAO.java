package model.dao;

import model.data.persistence.Administrateur;
import model.data.persistence.Secouriste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministrateurDAO {

    public List<Administrateur> findAll() {
        List<Administrateur> administrateurs = new ArrayList<>();

        try (Connection con = ConnexionBDD.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Administrateur")) {

            while (rs.next()) {
                Administrateur s = new Administrateur(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("email"),
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

    public Administrateur findAdministrateur(long id) {
        Administrateur administrateur = null;

        try (Connection con = ConnexionBDD.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM secouriste WHERE id=" + id)) {

            while (rs.next()) {
                administrateur = new Administrateur(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("dateNaissance"),
                        rs.getString("email"),
                        rs.getString("tel"),
                        rs.getString("adresse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrateur;
    }
}