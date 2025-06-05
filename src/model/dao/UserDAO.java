package model.dao;

import model.data.persistence.User;

import java.sql.*;

import static model.utils.PasswordHashing.*;

public class UserDAO {

    /**
     * Default constructor for UserDAO.
     */
    public UserDAO() {
        // Constructor can be used for initialization if needed
    }

    /**
     * Checks if a login already exists in the database.
     * @param login the login to check for existence.
     * @return true if the login exists, false otherwise.
     */
    public boolean doesLoginExist(String login) {
        boolean ret = false;
        String query = "SELECT login FROM user WHERE login = ?";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, login);  // on remplace le ? par la valeur du login
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ret = true;  // le login a été trouvé
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * Retrieves a User object from the database based on the provided login.
     * @param login the login of the user to retrieve.
     * @return a User object if found, or null if no user with that login exists.
     */
    public User getUserByLogin(String login) {
        User user = null;
        String query = "SELECT idUser, login, password FROM user WHERE login = ?";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, login);  // on remplace le ? par la valeur du login
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getLong("idUser"), rs.getString("login"), rs.getString("password"), rs.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



    /**
     * Changes the password of a user identified by their login.
     * @param login the login of the user whose password is to be changed.
     * @param newPassword the new password to set for the user.
     */
    public void changePasswordByLogin(String login, String newPassword) {
        // Récupère l'utilisateur à partir du login
        User user = getUserByLogin(login);

        if (user == null) {
            System.out.println("Utilisateur non trouvé avec le login : " + login);
            return;
        }

        String updateQuery = "UPDATE user SET password = ? WHERE idUser = ?";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(updateQuery)) {

            stmt.setString(1, hashPassword(newPassword));       // nouveau mot de passe
            stmt.setLong(2, user.getIdUser());        // identifiant unique du user

            int updated = stmt.executeUpdate();

            if (updated > 0) {
                System.out.println("Mot de passe mis à jour pour l'utilisateur avec l'ID : " + user.getIdUser());
            } else {
                System.out.println("Échec de la mise à jour du mot de passe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the User object containing the login and password of the new user.
     */
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO user (login, password) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(2, user.getRole());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // récupère l'ID auto-généré
                    user.setIdUser(generatedId);    // met à jour l'objet User si besoin
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage(), e);
        }

    }
}

