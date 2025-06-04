package model.data.service;

import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.data.persistence.User;

import java.sql.SQLException;
import java.util.Objects;

import static model.utils.PasswordHashing.hashPassword;
import static model.utils.PasswordHashing.verifyPassword;

public class AuthentificationManagement {

    /**
     * instance of Authentication
     */
    private static final AuthentificationManagement instance = new AuthentificationManagement();


    /**
     * This class manages the authentication of users.
     */
    private UserDAO userDAO;

    /**
     * Code for password recovery.
     */
    private long code;

    /**
     * User object representing the currently authenticated user.
     */
    private User user;

    /**
     * Constructor for AuthentificationManagement
     * Initializes the UserDAO to interact with user data.
     */
    public AuthentificationManagement() {
        this.userDAO = DAOFactory.getUserDAO();
    }

    public static AuthentificationManagement getInstanceAuthentificationManagement() { return instance; }

    /**
     * Registers a new user with the provided login and password.
     * The username must not be empty and must not already exist in the database.
     *
     * @param mail The username of the new user.
     * @param newPassword  The password for the new user.
     * @param newPasswordConfirmation Confirmation of the new password.
     * @return boolean indicating success or failure of the login.
     */
    public boolean register(String mail, String newPassword, String newPasswordConfirmation) throws SQLException {
        boolean didRegistrationWorked = false;

        System.out.println("Registration attempt for login: " + mail);
        if (mail != null && !mail.isEmpty() && !userDAO.doesLoginExist(mail) ) {
            if (newPassword != null && !newPassword.isEmpty() && newPassword.equals(newPasswordConfirmation)) {
                User user = new User(-1, mail, newPassword);
                userDAO.addUser(user);
                System.out.println("User registered successfully.");
                didRegistrationWorked = true;
            }
        }

        return didRegistrationWorked;
    }

    /**
     * Authenticates a user based on their login and password.
     *
     * @param mail    The login of the user.
     * @param password The password of the user.
     * @return true if the authentication is successful, false otherwise.
     */
    public boolean login(String mail, String password) {
        try {
            User user = userDAO.getUserByLogin(mail);
            if (user != null && verifyPassword(password, user.getPassword())) {
                this.user = user;
                return true;
            } else {
                throw new Exception("Invalid login or password.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
        }
        return false;
    }



    /**
     * Receives a code for password recovery based on the user's login.
     * The code is generated as a random number based on the current time.
     *
     * @param login The login of the user requesting the code.
     */
    public boolean ReceiveCode(String login) {
        if (userDAO.doesLoginExist(login)) {
            this.code = Math.abs(System.currentTimeMillis() % 1000000);
            this.user = userDAO.getUserByLogin(login);
            System.out.println("Code : " + this.code);
            return true;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

    /**
     * Changes the user's password if the provided code matches the stored code.
     *
     * @param code  The code to verify.
     * @param npw1  The new password.
     */
    public boolean changePassword(long code, String npw1) {
        System.out.print(this.code);
        if (code == this.code) {
            if (!Objects.equals(npw1, "") && npw1 != null) {
                String hashedPassword = hashPassword(npw1); // Hash du nouveau mot de passe
                userDAO.changePasswordByLogin(user.getLogin(), hashedPassword);
                user.setPassword(hashedPassword);
                System.out.println("Password changed successfully.");
                return true;
            }
        }
        return false;
    }



    /**
     * Get the current user loaded
     *
     * @return the user
     */
    public User getCurrentUser() {
        return this.user;
    }
}

