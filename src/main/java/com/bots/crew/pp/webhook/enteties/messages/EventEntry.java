package com.bots.crew.pp.webhook.enteties.messages;

import java.util.Date;
import java.util.List;

/**
 *
 */
public abstract class EventEntry {
    private String id;
    private Date time;
    private List<Messaging> messaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Messaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntry that = (EventEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
