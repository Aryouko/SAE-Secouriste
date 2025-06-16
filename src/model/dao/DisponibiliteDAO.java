package model.dao;

import model.data.persistence.Disponibilite;
import model.data.persistence.Journee;
import model.data.persistence.Secouriste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisponibiliteDAO {


    public ArrayList<Disponibilite> findById(Secouriste secouriste) {
        ArrayList<Disponibilite> disponibilites = new ArrayList<>();
        long id = secouriste.getIdSecouriste();
        String query = "SELECT * FROM Disponibilite JOIN Journee ON Journee.id = journeeDisp WHERE secouristeDisp = ?";
        try (Connection con = ConnectionBDD.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                disponibilites.add(new Disponibilite(secouriste, new Journee(rs.getInt("Jour"), rs.getInt("Mois"), rs.getInt("Annee"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disponibilites;
    }

    public void deleteByJour(int jour, int mois, int annee) {
        String query = "DELETE FROM Disponibilite WHERE jour = ? AND mois = ? AND annee = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, jour);
            stmt.setInt(2, mois);
            stmt.setInt(3, annee);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDisponibilite(long idSecouriste, long idJournee) {
        String query = "DELETE FROM Disponibilite WHERE secouristeDisp = ? AND journeeDisp = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idSecouriste);
            stmt.setLong(2, idJournee);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
