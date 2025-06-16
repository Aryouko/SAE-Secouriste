package controller.both;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.sql.*;
import java.util.ArrayList;

import static model.utils.FetchDatabaseCredentials.*;

public class MapController {

    @FXML
    private WebView mapWebView;

    private WebEngine webEngine;

    public void initialize() {
        webEngine = mapWebView.getEngine();
        // Load your local map.html file from resources
        String url = getClass().getResource("/map.html").toExternalForm();
        webEngine.load(url);

        // When page finishes loading, inject markers
        webEngine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                loadLocationsAndAddMarkers();
            }
        });
    }

    private void loadLocationsAndAddMarkers() {
        ArrayList<Location> locations = fetchLocationsFromDB();

        // Convert list to JSON string (simple manual way)
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            json.append("{\"lat\":").append(loc.lat)
                    .append(",\"lng\":").append(loc.lng)
                    .append(",\"name\":\"").append(loc.name.replace("\"", "\\\"")).append("\"}");
            if (i < locations.size() - 1) json.append(",");
        }
        json.append("]");

        // Inject locations into JS function addMarkers
        webEngine.executeScript("addMarkers(" + json.toString() + ");");
    }

    private ArrayList<Location> fetchLocationsFromDB() {
        ArrayList<Location> locations = new ArrayList<>();

        // Add 2 placeholder locations first:
        locations.add(new Location(48.8584, 2.2945, "Eiffel Tower"));
        locations.add(new Location(40.6892, -74.0445, "Statue of Liberty"));

        // Your MySQL connection settings here
        String url = getUrl();
        String user = getUsername();
        String password = getPassword();

        String query = "SELECT latitude, longitude, name FROM locations";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                double lat = rs.getDouble("latitude");
                double lng = rs.getDouble("longitude");
                String name = rs.getString("name");
                locations.add(new Location(lat, lng, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    private static class Location {
        double lat, lng;
        String name;
        Location(double lat, double lng, String name) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;
        }
    }
}
