package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectNumberOfTicketsRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.SendLocationRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

@Component
public class SelectCinemaObserver extends AbstractMessagingObserver {
    private UserReservationService userReservationService;
    private MovieSessionService movieSessionService;

    public SelectCinemaObserver(FacebookMessagingHandler handler,
                                MessageClient client,
                                MessengerUserService userService,
                                UserReservationService userReservationService) {
        super(handler, client, userService);
        this.userReservationService = userReservationService;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        String payload = (String) message.getMessage().getQuickReply().getPayload();
        MessagingRequest request;
        if (payload.equals("find")) {
            request = new SendLocationRequestBuilder(psid, "Please share your location").build();
            userService.save(psid, MessangerUserStatus.RQUEST_TO_SEND_LOCATION);
        } else {
            int cinemaId = Integer.parseInt(payload);
            UserReservation reservation = userReservationService.findUserLatestReservation(psid);
            userReservationService.saveCinema(cinemaId, reservation);
            int maxNumberOfTickets = movieSessionService.findMaxNumberOfTicketsForUserLastReservation(reservation.getId());
            request = new SelectNumberOfTicketsRequestBuilder(psid, maxNumberOfTickets).build();
            userService.save(psid, MessangerUserStatus.SELECT_NUMBER_OF_TICKETS);
        }
        this.client.sandMassage(request);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_CINEMA_QUICK_LIST;
    }
}
