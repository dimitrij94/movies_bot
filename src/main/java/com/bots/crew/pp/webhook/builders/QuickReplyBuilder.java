package com.bots.crew.pp.webhook.builders;

import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

public abstract class QuickReplyBuilder {

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

    protected abstract QuickReplyRequestContent getRequestContent();

}
