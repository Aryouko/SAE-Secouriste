package model.data.persistence;

public class User {

    private String username; // Username of the user
    private String password; // Password of the user

    /**
     * Constructor of User
     * @param username - username of the user
     * @param password - password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
