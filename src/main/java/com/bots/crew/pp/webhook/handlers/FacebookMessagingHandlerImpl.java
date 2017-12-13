package com.bots.crew.pp.webhook.handlers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.persistant_menu.PersistantMenuMessage;
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

@Component
public class FacebookMessagingHandlerImpl implements FacebookMessagingHandler {

    private ObjectMapper mapper;
    private final Class<Messaging> messagingClass = Messaging.class;
    private final String pageAccessKey;
    private Logger log = LoggerFactory.getLogger(FacebookMessagingHandlerImpl.class);
    private Map<MessangerUserStatus, AbstractMessagingObserver> observers = new EnumMap<>(MessangerUserStatus.class);
    private Map<MessangerUserStatus, PersistentMenuAbstractMessagingObserver> persistantMenuObservers = new EnumMap<>(MessangerUserStatus.class);
    private PersistantMenuService persistantMenuService;
    private UserReservationService reservationService;
    private MessengerUserService userService;

    public FacebookMessagingHandlerImpl(ObjectMapper mapper,
                                        Environment env,
                                        PersistantMenuService persistantMenuService,
                                        UserReservationService reservationService,
                                        MessengerUserService userService) {
        this.mapper = mapper;
        this.pageAccessKey = env.getProperty("facebook.test.page.access.token");
        this.persistantMenuService = persistantMenuService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    public boolean isJsonTreeMatching(JsonNode messagingNode) {
        return !messagingNode.path("message").isMissingNode();
    }

    @Override
    @Async
    public void execute(JsonNode json) throws IOException {
        Messaging messaging = mapper.treeToValue(json, messagingClass);
        MessengerUser user = userService.createIfNotExists(messaging);
        this.notify(messaging, user);
    }

    @Override
    public void notify(Messaging value, MessengerUser user) {
        UserReservation reservation = getUserReservation(user);
        PersistantMenuMessage menu = persistantMenuService.matchAndReturn(value);
        if (menu == null) {
            notifyMessengerObservers(user, value, reservation);
        } else {
            notifyPersistentMenuObservers(user, value, reservation, menu);
        }
    }

    @Override
    public void addPersistantMenuObserver(PersistentMenuAbstractMessagingObserver observer, MessangerUserStatus observableStatus) {
        this.persistantMenuObservers.put(observableStatus, observer);
    }

    private UserReservation notifyMessengerObservers(MessengerUser user, Messaging value, UserReservation reservation) {
        AbstractMessagingObserver observer = this.observers.get(user.getStatus());
        UserReservation updatedReservation;
        try {
            updatedReservation = observer.changeState(value, reservation);
        } catch (Throwable e) {
            e.printStackTrace();
            updatedReservation = null;
        }
        if (updatedReservation != null) {
            observer.forwardResponse(updatedReservation);
        } else {
            user = userService.find(user.getPsid());
            observer = this.observers.get(user.getPreviousStatus());
            observer.forwardResponse(reservation);
        }
        return updatedReservation;
    }

    private UserReservation notifyPersistentMenuObservers(MessengerUser user, Messaging value, UserReservation reservation, PersistantMenuMessage menu) {
        AbstractMessagingObserver observer = this.persistantMenuObservers.get(menu.getOptions());
        UserReservation updatedReservation = observer.changeState(value, reservation);
        observer.forwardResponse(updatedReservation);
        MessangerUserStatus userStatus = user.getPreviousStatus();
        userStatus = userStatus.equals(MessangerUserStatus.SELECT_TIME) ? MessangerUserStatus.GETTING_STARTED : userStatus;
        observer = this.observers.get(userStatus);
        observer.forwardResponse(updatedReservation);
        return updatedReservation;
    }

    private UserReservation getUserReservation(MessengerUser user) {
        UserReservation reservation;
        if (user.getStatus().equals(MessangerUserStatus.GETTING_STARTED)) {
            reservation = reservationService.saveEmptyReservation(user);
        } else {
            reservation = reservationService.findUserLatestReservation(user.getPsid());
        }
        return reservation;
    }

    @Override
    public void addObserver(AbstractMessagingObserver observer, MessangerUserStatus observableStatus) {
        this.observers.put(observableStatus, observer);
    }

    @Override
    public void removeObserver(MessangerUserStatus observerId) {
        observers.remove(observerId);
    }


}
