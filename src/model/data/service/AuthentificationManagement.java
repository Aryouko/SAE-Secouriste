package model.data.service;

import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.data.persistence.Secouriste;
import model.data.persistence.User;
import java.sql.SQLException;
import java.util.Objects;

import static model.dao.DAOFactory.getSecouristeDAO;
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
    private final UserDAO userDAO;

    /**
     * Code for password recovery.
     */
    private long code;

    /**
     * User object representing the currently authenticated user.
     */
    private User user;

    /**
     * User object representing the currently authenticated user.
     */
    private Secouriste secouriste;

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
                User user = new User(-1, mail, hashPassword(newPassword), "rescuer");
                userDAO.addUser(user);
                this.user = userDAO.getUserByLogin(mail);
                System.out.println("User registered successfully.");
                didRegistrationWorked = true;
            }
        }
        return didRegistrationWorked;
    }


    /**
     * Enum representing the possible results of a login attempt.
     */
    public enum LoginResult {
        SUCCESS,
        INVALID_LOGIN,
        INVALID_PASSWORD,
        INVALID_RESCUER,
        ERROR
    }

    /**
     * Authenticates a user based on their login and password.
     *
     * @param mail    The login of the user.
     * @param password The password of the user.
     * @return LoginResult indicating the result of the login attempt.
     */
    public LoginResult login(String mail, String password) {
        try {
            User user = userDAO.getUserByLogin(mail);
            if (user == null) {
                return LoginResult.INVALID_LOGIN;
            }
            if (!verifyPassword(password, user.getPassword())) {
                return LoginResult.INVALID_PASSWORD;
            }

            this.user = user; // Affecte l'utilisateur courant avant de vérifier le secouriste

            if (SecouristeIsCreated()) {
                this.secouriste = getSecouristeDAO().findById(this.user.getIdUser());
                return LoginResult.SUCCESS;
            } else {
                return LoginResult.INVALID_RESCUER;
            }

        } catch (Exception e) {
            this.user = null;
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
            return LoginResult.ERROR;
        }
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
            this.user = null;
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
            if (npw1 != null && !Objects.equals(npw1, "")) {
                String hashedPassword = hashPassword(npw1); // Hash du nouveau mot de passe
                userDAO.changePasswordByLogin(user.getLogin(), hashedPassword);
                user.setPassword(hashedPassword);
                System.out.println("Password changed successfully.");
                return true;
            }
        }
        this.user = null;
        return false;
    }

    public boolean createRescuer(long id, String nom, String prenom, String dateNaissance, String tel, String adresse) {

        this.secouriste = new Secouriste(id, nom, prenom, dateNaissance, tel, adresse);
        boolean ok = DAOFactory.getSecouristeDAO().addSecouriste(this.secouriste);
        if (ok) {
            System.out.println("Rescuer created successfully.");
        } else {
            System.out.println("Failed to create rescuer.");
        }
        return ok;
    }

    public boolean SecouristeIsCreated() {
        User currentUser = getInstanceAuthentificationManagement().getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        Secouriste secouriste = getSecouristeDAO().findById(currentUser.getIdUser());
        if (secouriste == null) {
            return false;
        }
        return secouriste.getNom() != null && !secouriste.getNom().isEmpty();
    }

    /**
     * Get the current user loaded
     *
     * @return the user
     */
    public User getCurrentUser() {
        return this.user;
    }

    /**
     * Get the current rescuer loaded
     *
     * @return the user
     */
    public Secouriste getCurrentRescuer() {
        return this.secouriste;
    }
}

