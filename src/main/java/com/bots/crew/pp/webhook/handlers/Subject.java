package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.observers.Observer;

public interface Subject<T> {
    void notify(T value);
    int addObserver(Observer observer);
    void removeObserver(int observerId);
}
