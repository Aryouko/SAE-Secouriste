package model.data.persistence;

import java.util.Date;

public class Notification {

    /**
     * the title of the notification
     */
    public String title;
    public String message;
    public String date;
    public long sender;
    public long recipient;
    public long idDps;
    public boolean getIsViewed;

    public Notification(String title, String message, String date, long sender, long recipient, long idDps, boolean isViewed) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.idDps = idDps;
        this.getIsViewed = isViewed;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public long getIdDPS() {
        return idDps; // Assuming recipient is the ID of the DPS
    }

    public long getSender() {
        return sender;
    }

    public long getRecipient() {
        return recipient;
    }

    public boolean getIsViewed() {
        return getIsViewed;
    }
}
