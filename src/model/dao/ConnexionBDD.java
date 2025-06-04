package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String user = "root";
    private static final String password = "azerty";
    private static final String url = "";
    private static Connection connexion;


    public static Connection getConnexion() throws SQLException {
        System.out.println("Connexion en cours");
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection( url, user, password);
        }
        return connexion;
    }
}