package model.dao;
import java.sql.*;
import java.util.ArrayList;

import javafx.scene.chart.ScatterChart;
import model.data.persistence.DPS;
import model.data.persistence.Site;
import model.data.persistence.Sport;
import model.data.persistence.Journee;

public class DPSDAO {

    public void insert(DPS dps) {
        String insertJourneeQuery = "INSERT INTO Journee (jour, mois, annee) VALUES (?, ?, ?)";
        String insertDPSQuery = "INSERT INTO DPS (id, name, horaire_depart, horaire_fin, site, sport, journee) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionBDD.getConnection()) {
            // Commencer transaction
            con.setAutoCommit(false);

            int journeeId;

            // Insertion dans Journee
            try (PreparedStatement stmt = con.prepareStatement(insertJourneeQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, dps.getJournee().getJour());
                stmt.setInt(2, dps.getJournee().getMois());
                stmt.setInt(3, dps.getJournee().getAnnee());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        journeeId = generatedKeys.getInt(1);
                    } else {
                        con.rollback();
                        throw new SQLException("Échec de la récupération de l'ID de la journée.");
                    }
                }
            }

            // Insertion dans DPS
            try (PreparedStatement stmt2 = con.prepareStatement(insertDPSQuery)) {
                stmt2.setLong(1, dps.getId());
                stmt2.setString(2, dps.getName());
                stmt2.setInt(3, dps.getHoraireDepart());
                stmt2.setInt(4, dps.getHoraireFin());
                stmt2.setLong(5, dps.getSite().getCode());
                stmt2.setLong(6, dps.getSport().getCode());
                stmt2.setInt(7, journeeId);
                stmt2.executeUpdate();
            }

            // Commit transaction
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public ArrayList<DPS> findAll () {
        ArrayList<DPS> dpsList = new ArrayList<>();
        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT d.ID, d.NAME, d.HORAIRE_DEPART, d.HORAIRE_FIN, j.JOUR, j.MOIS, j.ANNEE, s.CODE AS SITE_CODE, s.NOM AS SITE_NOM, s.LONGITUDE AS SITE_LON, s.LATITUDE AS SITE_LAT, sp.CODE AS SPORT_CODE, sp.NOM AS SPORT_NOM FROM DPS d JOIN Site s ON d.SITE = s.CODE JOIN Sport sp ON d.SPORT = sp.CODE JOIN Journee j ON j.ID = d.JOURNEE")) {
             while (rs.next()) {
                 // DPS
                 int id = rs.getInt("ID");
                 String name = rs.getString("NAME");
                 int horaireDepart = rs.getInt("HORAIRE_DEPART");
                 int horaireFin = rs.getInt("HORAIRE_FIN");

                 // Site
                 long siteCode = rs.getLong("SITE_CODE");
                 String siteNom = rs.getString("SITE_NOM");
                 Float siteLongitude = rs.getFloat("SITE_LON");
                 Float siteLatitude = rs.getFloat("SITE_LAT");
                 Site site = new Site(siteCode, siteNom, siteLongitude, siteLatitude); // adapte ce constructeur

                 // Sport
                 Long sportCode = rs.getLong("SPORT_CODE");
                 String sportNom = rs.getString("SPORT_NOM");
                 Sport sport = new Sport(sportCode, sportNom); // idem, adapte selon ton modèle

                 // Journee
                 int jour = rs.getInt("JOUR");
                 int mois = rs.getInt("MOIS");
                 int annee = rs.getInt("ANNEE");
                 Journee journee = new Journee(jour, mois, annee);

                 // DPS complet
                 DPS dps = new DPS(id, name, horaireDepart, horaireFin, site, sport, journee);
                 dpsList.add(dps);
             }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return dpsList;
    }

    public DPS findById(long idDps) {
        DPS ret = null;
        String query = "SELECT d.ID, d.NAME, d.HORAIRE_DEPART, d.HORAIRE_FIN, j.JOUR, j.MOIS, j.ANNEE, s.CODE AS SITE_CODE, s.NOM AS SITE_NOM, s.LONGITUDE AS SITE_LON, s.LATITUDE AS SITE_LAT, sp.CODE AS SPORT_CODE, sp.NOM AS SPORT_NOM FROM DPS d JOIN Site s ON d.SITE = s.CODE JOIN Sport sp ON d.SPORT = sp.CODE JOIN Journee j ON j.ID = d.JOURNEE WHERE d.ID = ?";
        try (Connection con = ConnectionBDD.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1,idDps);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // DPS
                String name = rs.getString("NAME");
                int horaireDepart = rs.getInt("HORAIRE_DEPART");
                int horaireFin = rs.getInt("HORAIRE_FIN");

                // Site
                long siteCode = rs.getLong("SITE_CODE");
                String siteNom = rs.getString("SITE_NOM");
                Float siteLongitude = rs.getFloat("SITE_LON");
                Float siteLatitude = rs.getFloat("SITE_LAT");
                Site site = new Site(siteCode, siteNom, siteLongitude, siteLatitude); // adapte ce constructeur

                // Sport
                Long sportCode = rs.getLong("SPORT_CODE");
                String sportNom = rs.getString("SPORT_NOM");
                Sport sport = new Sport(sportCode, sportNom); // idem, adapte selon ton modèle

                // Journee
                int jour = rs.getInt("JOUR");
                int mois = rs.getInt("MOIS");
                int annee = rs.getInt("ANNEE");
                Journee journee = new Journee(jour, mois, annee);

                ret = new DPS(idDps, name, horaireDepart, horaireFin, site, sport, journee);;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public ArrayList<String> findDPSName() {
        ArrayList<String> dpsNames = new ArrayList<>();
        String query = "SELECT NAME FROM DPS";
        try (Connection con = ConnectionBDD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                dpsNames.add(rs.getString("NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dpsNames;
    }

    public DPS findByName(String name) {
        DPS ret = null;
        String query = "SELECT d.ID, d.NAME, d.HORAIRE_DEPART, d.HORAIRE_FIN, j.JOUR, j.MOIS, j.ANNEE, s.CODE AS SITE_CODE, s.NOM AS SITE_NOM, s.LONGITUDE AS SITE_LON, s.LATITUDE AS SITE_LAT, sp.CODE AS SPORT_CODE, sp.NOM AS SPORT_NOM FROM DPS d JOIN Site s ON d.SITE = s.CODE JOIN Sport sp ON d.SPORT = sp.CODE JOIN Journee j ON j.ID = d.JOURNEE WHERE d.NAME = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("ID");
                int horaireDepart = rs.getInt("HORAIRE_DEPART");
                int horaireFin = rs.getInt("HORAIRE_FIN");

                long siteCode = rs.getLong("SITE_CODE");
                String siteNom = rs.getString("SITE_NOM");
                float siteLongitude = rs.getFloat("SITE_LON");
                float siteLatitude = rs.getFloat("SITE_LAT");
                Site site = new Site(siteCode, siteNom, siteLongitude, siteLatitude);

                long sportCode = rs.getLong("SPORT_CODE");
                String sportNom = rs.getString("SPORT_NOM");
                Sport sport = new Sport(sportCode, sportNom);

                int jour = rs.getInt("JOUR");
                int mois = rs.getInt("MOIS");
                int annee = rs.getInt("ANNEE");
                Journee journee = new Journee(jour, mois, annee);

                ret = new DPS(id, name, horaireDepart, horaireFin, site, sport, journee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
