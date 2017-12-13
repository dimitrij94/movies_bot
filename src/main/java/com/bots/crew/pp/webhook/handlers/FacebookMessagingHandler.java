package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.observers.persistant_menu.PersistentMenuAbstractMessagingObserver;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface FacebookMessagingHandler extends Subject<Messaging, MessangerUserStatus> {

    void execute(JsonNode json, MessengerUser user) throws IOException;

    void addObserver(PersistentMenuAbstractMessagingObserver observer, PersistantMenuOptions menuOption);
}
