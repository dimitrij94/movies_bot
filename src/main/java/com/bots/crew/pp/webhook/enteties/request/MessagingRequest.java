package com.bots.crew.pp.webhook.enteties.request;

import com.bots.crew.pp.webhook.enteties.recipient.Recipient;

import java.util.Date;

public class MessagingRequest {
    private Date timestamp;
    protected MessagingRequestContent message;
    protected Recipient recipient;

    public MessagingRequest() {
        timestamp = new Date();
    }

    public MessagingRequestContent getMessage() {
        return message;
    }

    public void setMessage(MessagingRequestContent message) {
        this.message = message;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagingRequest response = (MessagingRequest) o;

        if (message != null ? !message.equals(response.message) : response.message != null)
            return false;
        return recipient != null ? recipient.equals(response.recipient) : response.recipient == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        return result;
    }
}
