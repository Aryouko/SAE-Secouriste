package controller.both;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static model.utils.FetchDatabaseCredentials.*;

public class MapController {

    @FXML
    private WebView mapWebView;

    private WebEngine webEngine;

    public void initialize() {
        webEngine = mapWebView.getEngine();
        // Load your local map.html file from resources
        String url = getClass().getResource("/html/map.html").toExternalForm();
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
        // webEngine.executeScript("focusOnByName('Eiffel Tower')"); // Example to focus on a specific marker
    }

    private ArrayList<Location> fetchLocationsFromDB() {
        ArrayList<Location> locations = new ArrayList<>();

        String url = getUrl();
        String user = getUsername();
        String password = getPassword();

        String query = "SELECT name, latitude, longitude FROM site, dps WHERE site.code = dps.site";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Set<String> seenCoordinates = new HashSet<>();
            Random rand = new Random();

            while (rs.next()) {
                String name = rs.getString("name");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                String coordKey = latitude + "," + longitude;

                // laisser un peu d'espace entre les coordonnées pour éviter la superposition
                while (seenCoordinates.contains(coordKey)) {
                    latitude += (rand.nextDouble() - 0.5) * 0.001;
                    longitude += (rand.nextDouble() - 0.5) * 0.001;
                    coordKey = latitude + "," + longitude;
                }

                seenCoordinates.add(coordKey);
                locations.add(new Location(latitude, longitude, name));
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
