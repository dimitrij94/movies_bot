package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractMessagingObserver implements InitializingBean, Observer<Messaging> {
    protected FacebookMessagingHandler handler;
    protected MessageClient client;
    protected MessengerUserService userService;

    public AbstractMessagingObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService) {
        this.handler = handler;
        this.client = client;
        this.userService = userService;
    }




    @Override
    public void afterPropertiesSet() throws Exception {
        this.registerObserver();
    }

    protected void registerObserver(){
        handler.addObserver(this, getObservableStatus());
    }

    public abstract MessangerUserStatus getObservableStatus();
}
