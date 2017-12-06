package com.bots.crew.pp.webhook.enteties;

import java.util.List;

public class WebHookEvent {
    private String object;
    private List<FacebookEventEntry> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FacebookEventEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<FacebookEventEntry> entry) {
        this.entry = entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebHookEvent that = (WebHookEvent) o;

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
