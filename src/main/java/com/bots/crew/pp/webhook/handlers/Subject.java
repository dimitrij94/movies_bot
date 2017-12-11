package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.observers.AbstractMessagingObserver;

public interface Subject<T> {
    void notify(T value, MessengerUser user);
    void addObserver(AbstractMessagingObserver observer, MessangerUserStatus observableStatus);
    void removeObserver(int observerId);
}
