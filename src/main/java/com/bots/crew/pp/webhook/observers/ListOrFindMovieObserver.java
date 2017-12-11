package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.FindOrListMoviesQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import org.springframework.stereotype.Component;

@Component
public class ListOrFindMovieObserver extends AbstractMessagingObserver {

    public ListOrFindMovieObserver(FacebookMessagingHandler handler,
                                   MessageClient client,
                                   MessengerUserService userService) {
        super(handler, client, userService);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.GETTING_STARTED;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        MessagingRequest request =
                new FindOrListMoviesQuickRequestBuilder(psid).build();
        client.sendMassage(request);
        userService.setStatus(psid, MessangerUserStatus.SELECT_METHOD_OF_FINDING_MOVIE);
    }
}
