package com.bots.crew.pp.webhook.enteties.messages;

public class Postback {
    private Object payload;
    private String title;

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Postback postback = (Postback) o;

        if (payload != null ? !payload.equals(postback.payload) : postback.payload != null) return false;
        return title != null ? title.equals(postback.title) : postback.title == null;
    }

    @Override
    public int hashCode() {
        int result = payload != null ? payload.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
