package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.observers.AbstractMessagingObserver;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FacebookMessagingHandlerImpl implements FacebookMessagingHandler {

    private ObjectMapper mapper;
    private final Class<Messaging> messagingClass = Messaging.class;
    private final String pageAccessKey;
    private Logger log = LoggerFactory.getLogger(FacebookMessagingHandlerImpl.class);
    private Map<MessangerUserStatus, AbstractMessagingObserver> observers = new EnumMap<>(MessangerUserStatus.class);
    private Map<String, MessagingRequest> usersLastRequestMap = new ConcurrentHashMap<>();
    private MessageClient messageClient;

    public FacebookMessagingHandlerImpl(ObjectMapper mapper, Environment env, MessageClient messageClient1) {
        this.mapper = mapper;
        this.pageAccessKey = env.getProperty("facebook.test.page.access.token");
        this.messageClient = messageClient1;
    }

    public boolean isJsonTreeMatching(JsonNode messagingNode) {
        return !messagingNode.path("message").isMissingNode();
    }

    @Override
    @Async
    public void execute(JsonNode json, MessengerUser user) throws IOException {
        Messaging messaging = mapper.treeToValue(json, messagingClass);
        this.notify(messaging, user);
    }

    @Override
    public void notify(Messaging value, MessengerUser user) {
        this.observers.get(user.getStatus()).notify(value, user);
    }

    @Override
    public MessagingRequest getUserLastRequest(String psid) {
        return usersLastRequestMap.get(psid);
    }

    @Override
    public void addObserver(AbstractMessagingObserver observer, MessangerUserStatus observableStatus) {
        this.observers.put(observableStatus, observer);
    }

    @Override
    public void removeObserver(int observerId) {
        observers.remove(observerId);
    }


    /*
    *  private ResponseEntity<MessagingRequest> dispatchTextMessage(JsonNode json, String messageText) throws JsonProcessingException {
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
                new QuickReply("Quick", "Not enough RECURSION"),
                new QuickReply("Exit", "So you give up eh?"));

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
    }*/
}
