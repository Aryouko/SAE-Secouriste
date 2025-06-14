package model.dao;

import model.data.persistence.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    /**
     * findById find the notification by the id of the secouriste
     *
     * @return return the secouriste
     */
    public List<Notification> findById(long idSecouriste) {
        List<Notification> listNotifications = new ArrayList<>();
        String query = "SELECT * FROM Notification, Secouriste WHERE Notification.recipient = ?";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, idSecouriste);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Notification notification = new Notification(
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getString("date"),
                        rs.getLong("sender"),
                        rs.getLong("recipient")
                );

                listNotifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNotifications;
    }

    /**
     * save save the notification in the database
     *
     * @param notification the notification to save
     */
    public void insert(Notification notification) {
        String query = "INSERT INTO Notification (title, date, message, sender, recipient) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionBDD.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, notification.getTitle());
            stmt.setString(2, notification.getMessage());
            stmt.setString(3, notification.getDate());
            stmt.setLong(4, notification.getSender());
            stmt.setLong(5, notification.getRecipient());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
