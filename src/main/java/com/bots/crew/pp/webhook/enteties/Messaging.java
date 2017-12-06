package com.bots.crew.pp.webhook.enteties;

import java.util.Date;

public class Messaging {
    private Sender sender;
    private Sender recipient;
    private Date timestamp;
    private Message message;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Sender getRecipient() {
        return recipient;
    }

    public void setRecipient(Sender recipient) {
        this.recipient = recipient;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messaging messaging = (Messaging) o;

        if (sender != null ? !sender.equals(messaging.sender) : messaging.sender != null) return false;
        if (recipient != null ? !recipient.equals(messaging.recipient) : messaging.recipient != null) return false;
        if (timestamp != null ? !timestamp.equals(messaging.timestamp) : messaging.timestamp != null) return false;
        return message != null ? message.equals(messaging.message) : messaging.message == null;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
