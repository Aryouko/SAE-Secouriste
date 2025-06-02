package model.data.service;

import model.dao.UserDAO;
import model.data.persistence.User;

public class AuthentificationManagement {
    private UserDAO userDAO;

    public AuthentificationManagement() {
        this.userDAO = new UserDAO();
    }

    public boolean authenticate(String login, String password) {
        User user = userDAO.getUserByLogin(login);
        if (user == null) {
            return false; // login inexistant
        }
        return user.getPassword().equals(password); // ou vérification hash
    }
}
