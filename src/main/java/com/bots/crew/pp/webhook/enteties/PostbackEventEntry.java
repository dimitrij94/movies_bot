package com.bots.crew.pp.webhook.enteties;

public class PostbackEventEntry extends FacebookEventEntry {
    String postback;

    public String getPostback() {
        return postback;
    }

    public void setPostback(String postback) {
        this.postback = postback;
    }
}
