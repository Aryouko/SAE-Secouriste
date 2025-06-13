package model.data.persistence;

import java.util.Date;

public class Notification {

    /**
     * the title of the notification
     */
    public String title;
    public String message;
    public Date date;
    public int sender;
    public int recipient;

    public Notification(String title, String message, Date date, int sender, int recipient) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public int getSender() {
        return sender;
    }

    public int getRecipient() {
        return recipient;
    }
}
