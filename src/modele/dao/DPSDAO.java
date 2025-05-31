package modele.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util .*;
import modele.data.persistence.DPS;
import modele.data.persistence.Site;
import modele.data.persistence.Sport;
import modele.data.persistence.Journee;
import modele.dao.ConnexionBDD;

public class DPSDAO {
    public int insert(DPS dps) {
        String query = "INSERT INTO DPS VALUES (" + dps.getId() + "," + dps.getHoraireDepart() + "," + dps.getHoraireFin() + "," + dps.getSite().getCode() + "," + dps.getSport().getCode() + "," + dps.getJournee().getJour() + "," + dps.getJournee().getMois() + "," + dps.getJournee().getAnnee() + ")";
        try (ConnexionBDD con = new ConnexionBDD();
             Statement st = con.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    public void findAll () {
        try (ConnexionBDD con = new ConnexionBDD();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM DPS")) {
             while (rs.next()) {
                String nom = rs.getString("LOGIN");
                String pwd = rs.getString("PWD");
                users.add(new User(nom , pwd));
             }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return users;
    }
}
