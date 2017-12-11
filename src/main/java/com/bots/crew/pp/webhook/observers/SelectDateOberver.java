package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;

public class SelectDateOberver extends AbstractMessagingObserver {
    public SelectDateOberver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService) {
        super(handler, client, userService);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_SESSION_DATE;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {

    }
}
