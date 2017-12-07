package com.bots.crew.pp.webhook.enteties.replies;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class QuickReply {
    @JsonProperty("content_type")
    protected String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
