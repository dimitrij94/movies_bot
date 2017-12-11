package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.messages.Postback;
import com.bots.crew.pp.webhook.enteties.persistant_menu.PersistantMenuMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PersistantMenuService {
    private ObjectMapper mapper;
    private Class<PersistantMenuMessage> menuClass = PersistantMenuMessage.class;

    public PersistantMenuService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public PersistantMenuMessage matchesPersistantMenuSignature(Messaging message) {
        Postback posback = message.getPostback();
        if (posback != null) {
            String postbackPayload = (String) posback.getPayload();
            try {
                JsonNode node = mapper.readTree(postbackPayload);
                if (!node.path("persistent_menu").isMissingNode()) {
                    return mapper.treeToValue(node, menuClass);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
