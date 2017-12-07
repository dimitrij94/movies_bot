package com.bots.crew.pp.webhook.enteties.replies;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextQuickReply extends QuickReply {
    protected String title;

    @JsonProperty("image_url")
    protected String imageUrl;

    protected String payload;

    public TextQuickReply() {
        super.contentType = "text";
    }

    public TextQuickReply(String title, String payload) {
        super.contentType = "text";
        this.payload = payload;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
