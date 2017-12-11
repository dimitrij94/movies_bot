package com.bots.crew.pp.webhook;

import com.bots.crew.pp.webhook.enteties.Hub;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FacebookWebhook {

    private String verifycationKey;
    private Logger log = LoggerFactory.getLogger(FacebookWebhook.class);
    private String testAppAccessToken;
    private ObjectMapper objectMapper;
    private MessengerUserService userService;
    private FacebookMessagingHandler handler;

    public FacebookWebhook(Environment environment,
                           MessengerUserService userService,
                           ObjectMapper objectMapper,
                           FacebookMessagingHandler handler) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        verifycationKey = environment.getProperty("social.facebook.verification");
        testAppAccessToken = environment.getProperty("facebook.test.page.access.token");
        this.handler = handler;
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

    @PostMapping(value = "/webhook", consumes = "application/json", produces = "application/json")
    public ResponseEntity webhook(HttpServletRequest request) throws IOException, ServletException {
        JsonNode root = objectMapper.readTree(request.getInputStream());
        JsonNode webhookEvent = root.path("entry").path(0).path("messaging").path(0);
        MessengerUser user = userService.storeInSessionPSID(request, webhookEvent);
        handler.execute(webhookEvent, user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
