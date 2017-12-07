package com.bots.crew.pp.webhook.builders.generic_reply;

import com.bots.crew.pp.webhook.enteties.payload.Attachment;
import com.bots.crew.pp.webhook.enteties.payload.GenericReplyPayload;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateElement;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.MessageWithAttachmentsRequestContent;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;

import java.util.List;

public abstract class GenericReplyBuilder {
    String psid;

    public GenericReplyBuilder(String psid) {
        this.psid = psid;
    }

    public MessagingRequest build() {
        MessageWithAttachmentsRequestContent requestContent = new MessageWithAttachmentsRequestContent();
        requestContent.setAttachment(getAttachment());

        Recipient recipient = new Recipient();
        recipient.setId(psid);

        MessagingRequest request = new MessagingRequest();
        request.setMessage(requestContent);
        request.setRecipient(recipient);
        return request;
    }

    protected Attachment getAttachment() {
        Attachment requestAttachments = new Attachment();
        requestAttachments.setType("template");
        requestAttachments.setPayload(getPayload());
        return requestAttachments;
    }

    protected GenericReplyPayload getPayload() {
        GenericReplyPayload payload = new GenericReplyPayload();
        payload.setElements(getElements());
        payload.setTemplateType("generic");
        return payload;
    }

    protected abstract List<GenericTamplateElement> getElements();
}
