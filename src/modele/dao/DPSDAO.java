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
             Statement st = con.getConnexion().createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    public void findAll () {
        try (ConnexionBDD con = new ConnexionBDD();
             Statement st = con.getConnexion().createStatement();
             ResultSet rs = st.executeQuery("SELECT d.ID, d.HORAIRE_DEPART, d.HORAIRE_FIN, d.JOUR, d.MOIS, d.ANNEE, s.CODE AS SITE_CODE, s.NOM AS SITE_NOM, s.LONGITUDE AS SITE_LON, s.LATITUDE AS SITE_LAT, sp.CODE AS SPORT_CODE, sp.NOM AS SPORT_NOM FROM DPS d JOIN Site s ON d.CODE_SITE = s.CODE JOIN Sport sp ON d.CODE_SPORT = sp.CODE")) {
             while (rs.next()) {
                 // DPS
                 int id = rs.getInt("ID");
                 int horaireDepart = rs.getInt("HORAIRE_DEPART");
                 int horaireFin = rs.getInt("HORAIRE_FIN");

                 // Site
                 String siteCode = rs.getString("SITE_CODE");
                 String siteNom = rs.getString("SITE_NOM");
                 Float siteLongitude = rs.getFloat("SITE_LON");
                 Float siteLatitude = rs.getFloat("SITE_LAT");
                 Site site = new Site(siteCode, siteNom, siteLongitude, siteLatitude); // adapte ce constructeur

                 // Sport
                 String sportCode = rs.getString("SPORT_CODE");
                 String sportNom = rs.getString("SPORT_NOM");
                 Sport sport = new Sport(sportCode, sportNom); // idem, adapte selon ton modèle

                 // Journee
                 int jour = rs.getInt("JOUR");
                 int mois = rs.getInt("MOIS");
                 int annee = rs.getInt("ANNEE");
                 Journee journee = new Journee(jour, mois, annee);

                 // DPS complet
                 DPS dps = new DPS(id, horaireDepart, horaireFin, site, sport, journee);
             }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
}
