package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.persistant_menu.PersistantMenuMessage;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.observers.AbstractMessagingObserver;
import com.bots.crew.pp.webhook.observers.persistant_menu.PersistentMenuAbstractMessagingObserver;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.PersistantMenuService;
import com.bots.crew.pp.webhook.services.UserReservationService;
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
    private Map<PersistantMenuOptions, PersistentMenuAbstractMessagingObserver> persistantMenuObservers = new EnumMap<>(PersistantMenuOptions.class);
    private Map<String, MessagingRequest> usersLastRequestMap = new ConcurrentHashMap<>();
    private MessageClient messageClient;
    private PersistantMenuService persistantMenuService;
    private MessengerUserService userService;
    private UserReservationService reservationService;

    public FacebookMessagingHandlerImpl(ObjectMapper mapper,
                                        Environment env,
                                        MessageClient messageClient1,
                                        PersistantMenuService persistantMenuService,
                                        MessengerUserService userService,
                                        UserReservationService reservationService) {
        this.mapper = mapper;
        this.pageAccessKey = env.getProperty("facebook.test.page.access.token");
        this.messageClient = messageClient1;
        this.persistantMenuService = persistantMenuService;
        this.userService = userService;
        this.reservationService = reservationService;
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
        UserReservation reservation;
        if (user.getStatus().equals(MessangerUserStatus.GETTING_STARTED)) {
            reservation = reservationService.saveEmptyReservation(user);
        } else {
            reservation = reservationService.findUserLatestReservation(user.getPsid());
        }
        UserReservation updatedReservation;
        AbstractMessagingObserver observer;
        PersistantMenuMessage menu = persistantMenuService.matchesPersistantMenuSignature(value);
        if (menu == null) {
            observer = this.observers.get(user.getStatus());
            try {
                updatedReservation = observer.changeState(value, reservation);
            } catch (Throwable e) {
                e.printStackTrace();
                updatedReservation = null;
            }
            if (updatedReservation != null) {
                observer.forwardResponse(updatedReservation);
            } else {
                observer = this.observers.get(user.getPreviousStatus());
                observer.forwardResponse(reservation);
            }
        } else {
            observer = this.persistantMenuObservers.get(menu.getOptions());
            updatedReservation = observer.changeState(value, reservation);
            observer.forwardResponse(updatedReservation);

            observer = this.observers.get(user.getPreviousStatus());
            observer.forwardResponse(updatedReservation);
        }

    }

    private void rollback() {

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
    public void addObserver(PersistentMenuAbstractMessagingObserver observer, PersistantMenuOptions menuOption) {
        this.persistantMenuObservers.put(menuOption, observer);
    }

    @Override
    public void removeObserver(int observerId) {
        observers.remove(observerId);
    }


}
