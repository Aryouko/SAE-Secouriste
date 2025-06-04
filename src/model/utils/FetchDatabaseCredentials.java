package model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FetchDatabaseCredentials {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = FetchDatabaseCredentials.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading database credentials", ex);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUser() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
