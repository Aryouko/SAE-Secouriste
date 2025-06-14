package model.data.service;

import model.dao.AffectationDAO;
import model.dao.DPSDAO;
import model.dao.NotificationDAO;
import model.data.persistence.DPS;
import model.data.persistence.Notification;

import java.util.ArrayList;

public class NotificationManagement {

    /**
     * instance of DPSDao
     */
    private final DPSDAO dpsDAO = new DPSDAO();

    /**
     * instance of NotificationManagement
     */
    private final NotificationDAO notificationDAO = new NotificationDAO();

    /**
     * instance of AffectationDAO
     */
    private final AffectationDAO affectationDAO = new AffectationDAO();

    /**
     * instance of AuthentificationManagement
     */
    private final AuthentificationManagement authentificationManagement = AuthentificationManagement.getInstanceAuthentificationManagement();

    /**
     * Constructor for NotificationManagement
     * Initializes the DAO instances to interact with the database.
     */
    public NotificationManagement() {
    }

    /**
     * Retrieves all notifications.
     *
     * @return a list of all notifications
     */
    public ArrayList<Notification> getNotificationById(long id) {
        return notificationDAO.findById(id);
    }


    /**
     * Creates a notification and saves it to the database.
     *
     * @param title                the title of the notification
     * @param message              the message of the notification
     * @param date                 the date of the notification
     * @param recipientDPSGroupName the name of the DPS group to which the notification is sent
     */
    public void createNotification(String title, String message, String date ,String recipientDPSGroupName) {

        long sender = authentificationManagement.getCurrentUser().getIdUser();

        // Research the DPS by name with the value of the comboBox
        DPS dps = dpsDAO.findByName(recipientDPSGroupName);
        long iDDps = dps.getId();

        // Get the list of rescuer IDs associated with the DPS group
        ArrayList<Integer> listIdRescuer = affectationDAO.findIdRescuerByDPS(iDDps);

        boolean isViewed = false;

        // Create a notification for each rescuer in the DPS group
        for(int idRescuer : listIdRescuer) {
            Notification notification = new Notification(title, message, date, sender, idRescuer, isViewed);
            notificationDAO.insert(notification);
        }

        // Create a notification for the sender as well
        Notification notification = new Notification(title, message, date, sender, sender, true);
        notificationDAO.insert(notification);
    }
}
