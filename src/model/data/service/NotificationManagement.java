package model.data.service;

import model.data.persistence.Affectation;
import model.data.persistence.DPS;
import model.data.persistence.Secouriste;

import java.util.List;

public class NotificationManagement {


    public NotificationManagement() {
    }

    public void createNotification(String title, String message, DPS recipientGroup) {

        // Get the list of recipients from the DPS in all the all affectations
        // List<Integer> recipients = recipientGroup.getSecouriste(recipientGroup);
    }
}
