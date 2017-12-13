package com.bots.crew.pp.webhook.enteties.messages;

import java.util.List;

public class MessagingEventEntry extends EventEntry {
    private List<Messaging> messaging;

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
        if (!super.equals(o)) return false;

        MessagingEventEntry that = (MessagingEventEntry) o;

        return messaging != null ? messaging.equals(that.messaging) : that.messaging == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (messaging != null ? messaging.hashCode() : 0);
        return result;
    }
}
