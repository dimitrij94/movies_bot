package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.enteties.Messaging;
import com.bots.crew.pp.webhook.enteties.Sender;
import com.bots.crew.pp.webhook.enteties.response.MessagingResponse;
import com.bots.crew.pp.webhook.enteties.response.MessagingResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessagingHandler implements FacebookMessageHandler<MessagingResponse> {
    private ObjectMapper mapper;
    private final Class<Messaging> messagingClass = Messaging.class;

    public MessagingHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean isJsonTreeMatching(JsonNode webhookEvent) {
        return !webhookEvent.path("message").isMissingNode();
    }

    @Override
    public ResponseEntity<MessagingResponse> execute(String json) throws IOException {
        Messaging messaging = mapper.readValue(json, messagingClass);
        String messageText = messaging.getMessage().getText();
        if (messageText != null) {

            Sender recipient = new Sender();
            recipient.setId(messaging.getSender().getId());

            String responseText = messageText + " to you to :)";
            MessagingResponseContent responseContent = new MessagingResponseContent();
            responseContent.setText(responseText);

            MessagingResponse response = new MessagingResponse();
            response.setRecipiant(recipient);
            response.setResponseContent(responseContent);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return null;
    }
}
