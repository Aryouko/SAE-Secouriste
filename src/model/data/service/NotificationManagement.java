package model.data.service;

import model.dao.AffectationDAO;
import model.dao.NotificationDAO;
import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.persistence.Notification;
import model.data.persistence.Secouriste;

import java.util.ArrayList;
import java.util.List;

public class NotificationManagement {

    private NotificationDAO notificationDAO = new NotificationDAO();
    private final AffectationDAO affectationDAO = new AffectationDAO();

    public NotificationManagement() {
    }

    public void createNotification(String title, String message, String date, int sender ,DPS recipientGroup) {

        // Get the list of recipients from the DPS in all the all affectations
        // List<Integer> recipients = recipientGroup.getSecouriste(recipientGroup);

        long iDDPS = recipientGroup.getId();
        ArrayList<Integer> listIdRescuer = affectationDAO.findIdRescuerByDPS(iDDPS);

        for(int idRescuer : listIdRescuer) {
            Notification notif = new Notification(title, message, date, sender, idRescuer );
            notificationDAO.insert(notif);
        }
    }
}
