package model.dao;

import model.data.persistence.*;

import java.sql.*;
import java.util.ArrayList;

public class PossessionDAO {

    public void insert(Possession possession) {
        for (Competence competence : possession.competencesSec) {
            String query = "INSERT INTO Possession VALUES (" + competence.getIntitule() + "," + possession.getSecouriste().getIdSecouriste() + ")";
            try (Connection con = ConnexionBDD.getConnexion();
                 Statement stmt = con.createStatement()) {
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Possession find (Secouriste secouriste) {
        Possession ret = null;
        try (Connection con = ConnexionBDD.getConnexion();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Possession WHERE ID = " + secouriste.getIdSecouriste() + "")) {
            while (rs.next()) {
                long id = rs.getLong("ID");
                String intitule = rs.getString("INTITULE");

                ArrayList<Competence> competences = new ArrayList<>();

                competences.add(new Competence(intitule));
                ret = new Possession(competences, secouriste);
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return ret;
    }
}
