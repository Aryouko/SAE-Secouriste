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
                        rs.getDate("date"),
                        rs.getInt("sender"),
                        rs.getInt("recipient")
                );

                listNotifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNotifications;
    }
}
