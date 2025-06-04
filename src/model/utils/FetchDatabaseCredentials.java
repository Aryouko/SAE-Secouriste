package model.utils;

public class FetchDatabaseCredentials {
    private static String username;
    private static String password;
    private static String url;

    public FetchDatabaseCredentials() {
        String configPath = "login.json";
        String json = "";
        try (java.io.InputStream is = new java.io.FileInputStream(configPath)) {
            json = new String(is.readAllBytes());
            JSONObject obj = new org.json.JSONObject(json);
            username = obj.getString("username");
            password = obj.getString("password");
            url = obj.getString("url");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config from " + configPath, e);
        }


        this.username = username;
        this.password = password;
        this.url = url;
    }



    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }
}