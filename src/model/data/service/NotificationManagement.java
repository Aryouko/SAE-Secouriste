package model.data.service;

import model.dao.AffectationDAO;
import model.dao.DPSDAO;
import model.dao.NotificationDAO;
import model.data.persistence.DPS;
import model.data.persistence.Notification;

import java.util.ArrayList;

public class NotificationManagement {

    private final DPSDAO dpsDAO = new DPSDAO();
    private final NotificationDAO notificationDAO = new NotificationDAO();
    private final AffectationDAO affectationDAO = new AffectationDAO();
    private final AuthentificationManagement authentificationManagement = AuthentificationManagement.getInstanceAuthentificationManagement();

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

    public void createNotification(String title, String message, String date ,String recipientDPSGroupName) {

        long sender = authentificationManagement.getCurrentUser().getIdUser();

        // Research the DPS by name with the value of the comboBox
        DPS dps = dpsDAO.findByName(recipientDPSGroupName);
        long iDDps = dps.getId();

        ArrayList<Integer> listIdRescuer = affectationDAO.findIdRescuerByDPS(iDDps);

        boolean isViewed = false;

        for(int idRescuer : listIdRescuer) {
            Notification notification = new Notification(title, message, date, sender, idRescuer, isViewed);
            notificationDAO.insert(notification);
        }
    }
}
