package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessagerUserStatus;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.in.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.payload.*;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.replies.QuickReply;
import com.bots.crew.pp.webhook.enteties.replies.TextQuickReply;
import com.bots.crew.pp.webhook.enteties.request.MessageWithAttachmentsRequestContent;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequestContent;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;
import com.bots.crew.pp.webhook.observers.Observer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessagingHandler implements FacebookMessageHandler<MessagingRequest>, Subject<Messaging> {
    private ObjectMapper mapper;
    private final Class<Messaging> messagingClass = Messaging.class;
    private Environment env;
    private final String pageAccessKey;
    private Logger log = LoggerFactory.getLogger(MessagingHandler.class);
    private TextMessageClient textMessageClient;
    private Map<Integer, Observer> observers = new HashMap<>();
    private int idsCount;

    public MessagingHandler(ObjectMapper mapper, Environment env, TextMessageClient textMessageClient) {
        this.env = env;
        this.textMessageClient = textMessageClient;
        this.mapper = mapper;
        this.pageAccessKey = this.env.getProperty("facebook.test.page.access.token");
    }

    @Override
    public boolean isJsonTreeMatching(JsonNode messagingNode) {
        return !messagingNode.path("message").isMissingNode();
    }

    @Override
    public ResponseEntity<MessagingRequest> execute(JsonNode json, String psid, MessagerUserStatus status) throws IOException {
        Messaging messaging = mapper.treeToValue(json, messagingClass);
        this.notify(messaging);
        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<MessagingRequest> dispatchTextMessage(JsonNode json, String messageText) throws JsonProcessingException {
        Messaging messaging = mapper.treeToValue(json, messagingClass);
        switch (messageText) {
            case "Explain recursion":
                return repeatMessageAsQuickResponse(messaging);
            case "Show a Puppy":
                return sendPuppiesToChooseFrom(messaging);
            default:
                return repeatTextMessage(messaging);
        }
    }

    private ResponseEntity<MessagingRequest> sendPuppiesToChooseFrom(Messaging messaging) {

        GenericTamplateButtons buttonOne = new GenericTamplateButtons();
        buttonOne.setTitle("Pet the puppy");
        buttonOne.setPayload("Puppy petted");
        buttonOne.setType("postback");

        AttachmentDefaultAction defaultAction = new AttachmentDefaultAction();
        defaultAction.setType("web_url");
        defaultAction.setMessengerExtensions(true);
        defaultAction.setWebviewHeightRatio("compact");// compact || tall || full
        defaultAction.setUrl("https://google.com");

        GenericTamplateElement elementOne = new GenericTamplateElement();
        elementOne.setTitle("Puppy #1");
        elementOne.setSubtitle("Is`t he adorable");
        elementOne.setImageUrl("https://scontent.fiev4-1.fna.fbcdn.net/v/t34.0-0/p280x280/24891847_870635339758054_1733033309_n.jpg?oh=ca8e07838d6377fdbbaa6b43276d45a6&oe=5A2B34E7");
        elementOne.setDefaultAction(defaultAction);
        elementOne.setButtons(Arrays.asList(buttonOne));

        GenericReplyPayload payload = new GenericReplyPayload();
        payload.setTemplateType("generic");
        payload.setElements(Arrays.asList(elementOne));

        Attachment requestAttachments = new Attachment();
        requestAttachments.setType("template");
        requestAttachments.setPayload(payload);


        MessageWithAttachmentsRequestContent requestContent = new MessageWithAttachmentsRequestContent();
        requestContent.setAttachment(requestAttachments);

        Recipient recipient = new Recipient();
        recipient.setId(messaging.getSender().getId());

        MessagingRequest request = new MessagingRequest();
        request.setMessage(requestContent);
        request.setRecipient(recipient);

        this.textMessageClient.sandMassage(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<MessagingRequest> repeatMessageAsQuickResponse(Messaging messaging) {
        MessagingRequest request = new MessagingRequest();

        Recipient recipient = new Recipient();
        recipient.setId(messaging.getSender().getId());

        request.setRecipient(recipient);

        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("This is recursion baby ;)");
        List<QuickReply> replyList = Arrays.asList(
                new TextQuickReply("Quick", "Not enough RECURSION"),
                new TextQuickReply("Exit", "So you give up eh?"));

        requestContent.setQuickReplies(replyList);
        request.setMessage(requestContent);

        this.textMessageClient.sandMassage(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<MessagingRequest> repeatTextMessage(Messaging messaging) {
        String messageText = messaging.getMessage().getText();
        MessagingRequest request = new MessagingRequest();

        Recipient recipient = new Recipient();
        recipient.setId(messaging.getSender().getId());

        String responseText = messageText + " to you to :)";
        MessagingRequestContent responseContent = new MessagingRequestContent();
        responseContent.setText(responseText);

        request.setRecipient(recipient);
        request.setMessage(responseContent);

        this.textMessageClient.sandMassage(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<MessagingRequest> handleAttachmentsMessage(JsonNode attachmentsNode) {
        String attachmentUrl = attachmentsNode.path(0).path("payload").path("url").asText();
        return null;
    }

    @Override
    public void notify(Messaging value) {
        for (Integer key : this.observers.keySet()) {
            this.observers.get(key).notify(value);
        }
    }

    @Override
    public int addObserver(Observer observer) {
        this.idsCount += 1;
        this.observers.put(idsCount, observer);
        return idsCount;
    }

    @Override
    public void removeObserver(int observerId) {
        observers.remove(observerId);
    }
}
