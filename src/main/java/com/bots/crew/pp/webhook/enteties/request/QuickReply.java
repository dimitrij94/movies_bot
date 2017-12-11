package com.bots.crew.pp.webhook.enteties.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuickReply {

    /**
     * default value is text change to location for location quick reply
     */
    @JsonProperty("content_type")
    private String contentType = "text";

    private String title;

    @JsonProperty("image_url")
    private String imageUrl;

    private Object payload;

    public QuickReply() {
    }

    public QuickReply(String title, Object payload) {
        this.title = title;
        this.payload = payload;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuickReply that = (QuickReply) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        return payload != null ? payload.equals(that.payload) : that.payload == null;
    }

    @Override
    public int hashCode() {
        int result = contentType != null ? contentType.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
