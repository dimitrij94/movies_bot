package com.bots.crew.pp.webhook;

import com.bots.crew.pp.webhook.enteties.Hub;
import com.bots.crew.pp.webhook.handlers.FacebookMessageHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
public class FacebookWebhook {

    private Environment environment;
    private String verifycationKey;
    private String facebookAppId;
    private String facebookAppPassword;
    private Logger log = LoggerFactory.getLogger(FacebookWebhook.class);
    private String testAppAccessToken;
    private List<FacebookMessageHandler> handlers;
    private ObjectMapper objectMapper;


    public FacebookWebhook(Environment environment, ObjectMapper objectMapper, List<FacebookMessageHandler> handlers) {
        this.environment = environment;
        this.objectMapper = objectMapper;
        this.handlers = handlers;
        verifycationKey = this.environment.getProperty("social.facebook.verification");
        facebookAppId = this.environment.getProperty("social.facebook.app.id");
        facebookAppPassword = this.environment.getProperty("social.facebook.app.password ");
        testAppAccessToken = this.environment.getProperty("facebook.test.page.access.token");
    }

    @GetMapping("/webhook")
    public ResponseEntity verifyToken(@RequestParam("hub.verify_token") String verifyToken,
                                      @RequestParam("hub.challenge") String challange,
                                      @RequestParam("hub.mode") String mode) {
        Hub hub = new Hub(mode, verifyToken, challange);
        if (hub.getMode() != null && hub.getVerificationToken() != null) {
            log.debug(String.format("connection established with %s", hub.getVerificationToken()));
            return ResponseEntity.ok(hub.getChallenge());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/webhook", consumes = "application/json")
    public ResponseEntity webhook(HttpServletRequest request) throws IOException, ServletException {
        JsonNode root = objectMapper.readTree(request.getInputStream());
        JsonNode webhookEvent = root.path("entry").path(0).path("messaging").path(0);
        FacebookMessageHandler handler = findFittingHandler(webhookEvent);

        return  handler.execute(objectMapper.writeValueAsString(webhookEvent));


    }

    private FacebookMessageHandler findFittingHandler(JsonNode webhookEvent) {
        for (FacebookMessageHandler handler : handlers) {
            if (handler.isJsonTreeMatching(webhookEvent)) return handler;
        }
        return null;
    }

}
