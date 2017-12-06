package com.bots.crew.pp.webhook.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FacebookMessageHandler<T> {
    boolean isJsonTreeMatching(JsonNode root);
    ResponseEntity<T> execute(String json) throws IOException;
}
