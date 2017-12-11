package com.bots.crew.pp.webhook.enteties.messages;

import java.util.List;

/**
 * Root of the client request recieved each time the user writes a message
 */
public class ClientInputEvent {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientInputEvent that = (ClientInputEvent) o;

        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        return entry != null ? entry.equals(that.entry) : that.entry == null;
    }

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (entry != null ? entry.hashCode() : 0);
        return result;
    }
}
