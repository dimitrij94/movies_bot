package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;

public interface Observer<T> {
    UserReservation changeState(T message, UserReservation reservation);

    void forwardResponse(UserReservation reservation);

}
