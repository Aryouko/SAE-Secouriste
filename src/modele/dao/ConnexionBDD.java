package modele.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static Connection connexion;

    public static Connection getConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection("jdbc:mysql://server.sauveteur:3306/SAE", "admin", "admin");
        }
        return connexion;
    }
}