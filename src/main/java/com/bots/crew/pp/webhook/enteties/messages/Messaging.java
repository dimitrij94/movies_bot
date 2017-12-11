package com.bots.crew.pp.webhook.enteties.messages;

import com.bots.crew.pp.webhook.enteties.recipient.Recipient;

import java.util.Date;

public class Messaging {
    private Recipient sender;
    private Recipient recipient;
    private Date timestamp;
    private Message message;
    private Postback postback;

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
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

    public Recipient getSender() {
        return sender;
    }

    public void setSender(Recipient sender) {
        this.sender = sender;
    }

    public Postback getPostback() {
        return postback;
    }

    public void setPostback(Postback postback) {
        this.postback = postback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messaging messaging = (Messaging) o;

        if (recipient != null ? !recipient.equals(messaging.recipient) : messaging.recipient != null) return false;
        if (recipient != null ? !recipient.equals(messaging.recipient) : messaging.recipient != null) return false;
        if (timestamp != null ? !timestamp.equals(messaging.timestamp) : messaging.timestamp != null) return false;
        return message != null ? message.equals(messaging.message) : messaging.message == null;
    }

    @Override
    public int hashCode() {
        int result = recipient != null ? recipient.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
