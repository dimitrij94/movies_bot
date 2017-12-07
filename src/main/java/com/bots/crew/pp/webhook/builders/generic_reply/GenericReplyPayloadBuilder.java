package com.bots.crew.pp.webhook.builders.generic_reply;

import com.bots.crew.pp.webhook.enteties.payload.GenericReplyPayload;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateElement;

import java.util.List;

public abstract class GenericReplyPayloadBuilder {
    public GenericReplyPayload build() {
        GenericReplyPayload payload = new GenericReplyPayload();
        payload.setElements(getElements());
        payload.setTemplateType("generic");
        return payload;
    }

    protected abstract List<GenericTamplateElement> getElements();
}
