package com.bots.crew.pp.webhook.enteties.in.messages;


import com.bots.crew.pp.webhook.enteties.payload.Attachment;

import java.util.List;

public class MessageWithAtachments extends Message {
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}

