package com.bots.crew.pp.webhook.enteties.payload;

public class Attachment {
    protected String type;
    protected GenericReplyPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GenericReplyPayload getPayload() {
        return payload;
    }

    public void setPayload(GenericReplyPayload payload) {
        this.payload = payload;
    }
}
