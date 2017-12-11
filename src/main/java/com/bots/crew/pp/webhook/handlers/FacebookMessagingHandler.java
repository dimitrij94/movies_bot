package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.observers.persistant_menu.PersistentMenuAbstractMessagingObserver;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface FacebookMessagingHandler extends Subject<Messaging>{

    void execute(JsonNode json, MessengerUser user) throws IOException;

    MessagingRequest getUserLastRequest(String psid);

    void addObserver(PersistentMenuAbstractMessagingObserver observer, PersistantMenuOptions menuOption);
}
