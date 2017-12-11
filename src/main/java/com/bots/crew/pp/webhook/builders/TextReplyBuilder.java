package com.bots.crew.pp.webhook.builders;

import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequestContent;

public abstract class TextReplyBuilder implements ReplyBuilder{

    public MessagingRequest build() {
        MessagingRequest request = new MessagingRequest();
        request.setRecipient(getRecipient());
        request.setMessage(getRequestContent());
        return request;
    }

    protected Recipient getQuickRecipient(String recipientId) {
        Recipient recipient = new Recipient();
        recipient.setId(recipientId);
        return recipient;
    }

    protected abstract Recipient getRecipient();

    protected abstract MessagingRequestContent getRequestContent();

    public abstract void setMessage(String message);

    public abstract void setPsid(String psid);
}
