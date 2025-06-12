package model.dao;

import model.data.persistence.Disponibilite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DisponibiliteDAO {



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

}
