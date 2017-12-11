package com.bots.crew.pp.webhook.observers.persistant_menu;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.bots.crew.pp.webhook.builders.generic.user_reservation.UserReservationdRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.GettingStartedQuickReplyBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.persistant_menu.PersistantMenuMessage;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllReservationsObserver extends PersistentMenuAbstractMessagingObserver {
    private UserReservationService userReservationService;

    public ShowAllReservationsObserver(FacebookMessagingHandler handler, TextMessageClient client, MessengerUserService userService, ObjectMapper mapper, UserReservationService userReservationService) {
        super(handler, client, userService, mapper);
        this.userReservationService = userReservationService;
    }

    @Override
    public void notify(Messaging message, MessengerUser user, PersistantMenuMessage menuMessage) {
        String psid = user.getPsid();
        List<UserReservation> userReservations = userReservationService.findLast10ActiveReservations(psid);
        MessagingRequest request = new UserReservationdRequestBuilder(psid, userReservations).build();
        ((TextMessageClient)client).sendTextMessage(psid, "Sure, here are all of your reservations.");
        client.sendMassage(request);
        request = new GettingStartedQuickReplyBuilder(psid,"Do you want to add another one?").build();
        userService.setStatus(psid, MessangerUserStatus.GETTING_STARTED);
        userReservationService.deleteNotActiveReservations(psid);
        client.sendMassage(request);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return null;
    }

    @Override
    protected PersistantMenuOptions getObservableOption() {
        return PersistantMenuOptions.SHOW_ALL_RESERVATIONS;
    }
}
