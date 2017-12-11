package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;

public interface Observer<T> {
    void notify(T message, MessengerUser user);
}
