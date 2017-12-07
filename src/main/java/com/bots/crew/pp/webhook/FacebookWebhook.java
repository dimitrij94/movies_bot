package com.bots.crew.pp.webhook;

import com.bots.crew.pp.webhook.enteties.Hub;
import com.bots.crew.pp.webhook.handlers.FacebookMessageHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@Scope("session")
public class FacebookWebhook {

    private Environment environment;
    private String verifycationKey;
    private String facebookAppId;
    private String facebookAppPassword;
    private Logger log = LoggerFactory.getLogger(FacebookWebhook.class);
    private String testAppAccessToken;
    private List<FacebookMessageHandler> handlers;
    private ObjectMapper objectMapper;
    private MessagerUserStatus status = MessagerUserStatus.GETTING_STARTED;
    private String psid;

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

    @PostMapping(value = "/webhook", consumes = "application/json", produces = "application/json")
    public ResponseEntity webhook(HttpServletRequest request) throws IOException, ServletException {
        JsonNode root = objectMapper.readTree(request.getInputStream());
        JsonNode webhookEvent = root.path("entry").path(0).path("messaging").path(0);
        String psid = this.storeInSessionPSID(request, webhookEvent);
        FacebookMessageHandler handler = findFittingHandler(webhookEvent);
        ResponseEntity s = handler.execute(webhookEvent, psid, status);
        return s;
    }

    private String storeInSessionPSID(HttpServletRequest request, JsonNode messaging) {
        String psid = (String) request.getAttribute("PSID");
        if (psid != null) {
            this.psid = psid;
            psid = messaging.path("sender").path("id").asText();
            request.setAttribute("PSID", psid);
        }
        return psid;
    }

    private FacebookMessageHandler findFittingHandler(JsonNode webhookEvent) {
        for (FacebookMessageHandler handler : handlers) {
            if (handler.isJsonTreeMatching(webhookEvent)) return handler;
        }
        return null;
    }

    public String getPSID(){
        return this.psid;
    }

    public MessagerUserStatus getStatus() {
        return status;
    }

    public void setStatus(MessagerUserStatus status) {
        this.status = status;
    }
}
