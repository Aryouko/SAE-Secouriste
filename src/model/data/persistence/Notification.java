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

    public Notification(String title, String message, String date, long sender, long recipient) {
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

    public String getDate() {
        return date;
    }

    public long getSender() {
        return sender;
    }

    public long getRecipient() {
        return recipient;
    }
}
