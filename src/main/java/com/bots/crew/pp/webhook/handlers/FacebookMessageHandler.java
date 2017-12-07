package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessagerUserStatus;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FacebookMessageHandler<T> {
    /**
     * @param messagingNode the first element of messaging element corresponds
     *                      to json representation of Messaging class
     * @return whether or not the handler is responsible for handeling this request
     */
    boolean isJsonTreeMatching(JsonNode messagingNode);

    ResponseEntity<T> execute(JsonNode json, String psid, MessagerUserStatus status) throws IOException;

}
