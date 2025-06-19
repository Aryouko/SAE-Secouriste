package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static model.utils.FetchDatabaseCredentials.*;

public class ConnectionBDD {
    private static final String username = getUsername();
    private static final String password = getPassword();
    private static final String url = getUrl();
    private static Connection connexion;


    public static Connection getConnection() throws SQLException {
        // System.out.println("Connexion en cours");
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection( url, username, password);
            // System.out.println(url + " " + username);
            // System.out.println("Connexion réussie");
        }
        return connexion;
    }
}