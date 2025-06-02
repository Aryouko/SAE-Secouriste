package modele.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String user = "admin";
    private static final String password = "admin";
    private static final String url = "jdbc:mysql://server.sauveteur:3306/SAE";

    private static Connection connexion;


    public static Connection getConnexion() throws SQLException {
        System.out.println("Connexion en cours");
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection( url, user, password);
        }
        return connexion;
    }
}