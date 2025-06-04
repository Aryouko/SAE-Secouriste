package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static model.utils.FetchDatabaseCredentials.*;

public class ConnexionBDD {
    private static final String username = getUser();
    private static final String password = getPassword();
    private static final String url = getUrl();
    private static Connection connexion;


    public static Connection getConnexion() throws SQLException {
        System.out.println("Connexion en cours");
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection( url, username, password);
        }
        return connexion;
    }
}