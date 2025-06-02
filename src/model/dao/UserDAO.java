package model.dao;

import model.data.persistence.User;

import java.sql.*;

public class UserDAO {

    public User getUserByLogin(String login) {
        User user = null;
        String query = "SELECT id, login, password FROM user WHERE login = ?";

        try (Connection con = ConnexionBDD.getConnexion();
            PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, login);  // on remplace le ? par la valeur du login
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("login"), rs.getString("password"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

