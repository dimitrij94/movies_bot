package com.bots.crew.pp.webhook.observers.persistant_menu;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.bots.crew.pp.webhook.builders.generic.user_reservation.UserReservationdRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.persistant_menu.PersistantMenuMessage;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.observers.AbstractMessagingObserver;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.PersistantMenuService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class PersistentMenuAbstractMessagingObserver extends AbstractMessagingObserver {
    private ObjectMapper mapper;
    private PersistantMenuService persistantMenuService;

    public PersistentMenuAbstractMessagingObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, ObjectMapper mapper) {
        super(handler, client, userService);
        this.mapper = mapper;
    }

    public abstract void notify(Messaging message, MessengerUser user, PersistantMenuMessage menuMessage);

    @Override
    public void notify(Messaging message, MessengerUser user) {
    }

    public abstract MessangerUserStatus getObservableStatus();


    protected void registerObserver() {
        handler.addObserver(this, getObservableOption());
    }


    protected abstract PersistantMenuOptions getObservableOption();
}
