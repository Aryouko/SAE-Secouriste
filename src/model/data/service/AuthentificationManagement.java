package model.data.service;

import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.data.persistence.User;

import java.util.Objects;

import static model.utils.PasswordHashing.verifyPassword;

public class AuthentificationManagement {

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


    /**
     * Registers a new user with the provided login and password.
     * The username must not be empty and must not already exist in the database.
     *
     * @param mail The username of the new user.
     * @param newPassword  The password for the new user.
     * @param newPasswordConfirmation Confirmation of the new password.
     * @return boolean indicating success or failure of the login.
     */
    public boolean register(String mail, String newPassword, String newPasswordConfirmation) {
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
        User user = userDAO.getUserByLogin(mail);
        if (user == null) {
            System.out.println("Authentication failed: user not found for login " + mail);
            return false;
        }

        if (verifyPassword(password, user.getPassword())) {
            System.out.println("Authentication successful for user: " + mail);
            this.user = user;
            return true;
        }

        System.out.println("Authentication failed: incorrect password for user " + mail);
        return false;
    }



    /**
     * Receives a code for password recovery based on the user's login.
     * The code is generated as a random number based on the current time.
     *
     * @param login The login of the user requesting the code.
     */
    public void ReceiveCode(String login) {
        if (userDAO.doesLoginExist(login)) {

            this.code = Math.abs(System.currentTimeMillis() % 1000000);
            this.user = userDAO.getUserByLogin(login);
            System.out.println("Code : " + this.code);
        } else {
            System.out.println("User not found.");
        }
    }

    /**
     * Changes the user's password if the provided code matches the stored code.
     *
     * @param code  The code to verify.
     * @param npw1  The new password.
     * @param npw2  Confirmation of the new password.
     */
    public void changePassword(long code, String npw1, String npw2 ) {
        if (code == this.code) {
            if (!Objects.equals(npw1, "") && npw1 != null && npw1.equals(npw2)) {
                userDAO.changePasswordByLogin(user.getLogin(), npw1);
                user.setPassword(npw1);
                System.out.println("Password changed successfully.");
            }
        }
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

