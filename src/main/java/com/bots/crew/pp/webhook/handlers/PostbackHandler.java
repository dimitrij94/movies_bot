package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.enteties.PostbackEventEntry;
import com.bots.crew.pp.webhook.enteties.response.PostbackResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PostbackHandler implements FacebookMessageHandler<PostbackResponse> {
    Logger log = LoggerFactory.getLogger(PostbackHandler.class);
    Class<PostbackEventEntry> postbackEventEntryClass = PostbackEventEntry.class;
    ObjectMapper mapper;

    public PostbackHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean isJsonTreeMatching(JsonNode root) {
        return root.get("postback") != null;
    }

    @Override
    public ResponseEntity<PostbackResponse> execute(String json) {
        log.debug("Postback executed baby");
        return new ResponseEntity<PostbackResponse>(new PostbackResponse(), HttpStatus.OK);
    }
}
