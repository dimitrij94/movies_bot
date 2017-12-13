package com.bots.crew.pp.webhook.enteties.messages;

import java.util.List;

public class MessageRoot {
    private String object;
    private List<EventEntry> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<EventEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<EventEntry> entry) {
        this.entry = entry;
    }
}
