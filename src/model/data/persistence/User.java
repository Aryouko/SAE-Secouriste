package model.data.persistence;

public class User {

    /**
     * Informations of the user : id, login and password.
     */
    private long idUser;

    /**
     * Unique identifier of the user
     */
    private String login; // Username of the user

    /**
     * Password of the user
     */
    private String password; // Password of the user


    /**
     * Constructor of User
     * @param idUser - id of the user
     * @param login - username of the user
     * @param password - password of the user
     */
    public User(long idUser, String login, String password) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
    }

    /**
     * Getter of Login
     * @return the username of the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter of Login
     * @param login - the username of the user to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter of Password
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of Password
     * @param password - the password of the user to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of IdUser
     * @return the id of the user
     */
    public long getIdUser() {
        return idUser;
    }

    /**
     * Setter of IdUser
     * @param idUser - the id of the user to set
     */
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

}
