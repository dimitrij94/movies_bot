package com.bots.crew.pp.webhook.enteties.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttachmentDefaultAction {
    private String type;

    private String url;

    @JsonProperty("messenger_extensions")
    private boolean messengerExtensions;

    @JsonProperty("webview_height_ratio")
    private String webviewHeightRatio;

    @JsonProperty("fallback_url")
    private String fallbackUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMessengerExtensions() {
        return messengerExtensions;
    }

    public void setMessengerExtensions(boolean messengerExtensions) {
        this.messengerExtensions = messengerExtensions;
    }

    public String getWebviewHeightRatio() {
        return webviewHeightRatio;
    }

    public void setWebviewHeightRatio(String webviewHeightRatio) {
        this.webviewHeightRatio = webviewHeightRatio;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }
}
