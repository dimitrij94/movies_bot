package com.bots.crew.pp.webhook.observers.persistant_menu;

import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.observers.AbstractMessagingObserver;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.PersistantMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class PersistentMenuAbstractMessagingObserver extends AbstractMessagingObserver {
    protected ObjectMapper mapper;
    protected PersistantMenuService persistantMenuService;

    public PersistentMenuAbstractMessagingObserver(FacebookMessagingHandler handler,
                                                   MessageClient client,
                                                   MessengerUserService userService,
                                                   ObjectMapper mapper) {
        super(handler, client, userService);
        this.mapper = mapper;
    }


    protected void registerObserver() {
        handler.addPersistantMenuObserver(this, getObservableStatus());
    }

}
