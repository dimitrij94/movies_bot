package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessagerUserStatus;

public interface Observer<T> {
    void notify(T message);
}
