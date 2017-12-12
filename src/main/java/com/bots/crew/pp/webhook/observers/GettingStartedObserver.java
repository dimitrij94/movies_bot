package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.FindOrListMoviesQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import org.springframework.stereotype.Component;

@Component
public class GettingStartedObserver extends AbstractMessagingObserver {

    public GettingStartedObserver(FacebookMessagingHandler handler,
                                  MessageClient client,
                                  MessengerUserService userService) {
        super(handler, client, userService);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.GETTING_STARTED;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        return reservation;
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        MessengerUser user = reservation.getUser();
        MessagingRequest request =
                new FindOrListMoviesQuickRequestBuilder(user.getPsid()).build();
        client.sendMassage(request);
        userService.setStatus(user, MessangerUserStatus.SELECT_LIST_OR_FIND, getObservableStatus());
    }
}
