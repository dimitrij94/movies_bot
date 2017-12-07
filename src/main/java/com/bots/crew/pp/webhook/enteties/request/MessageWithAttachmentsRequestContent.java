package com.bots.crew.pp.webhook.enteties.request;

import com.bots.crew.pp.webhook.enteties.payload.Attachment;

public class MessageWithAttachmentsRequestContent extends MessagingRequestContent {
    private Attachment attachment;

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
