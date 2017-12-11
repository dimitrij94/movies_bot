package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectNumberOfTicketsRequestBuilder;
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
public class SelectNearestCinemaObserver extends AbstractMessagingObserver {
    private MessengerUserService userService;
    private UserReservationService reservationService;
    private MovieSessionService movieSessionService;

    public SelectNearestCinemaObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, MessengerUserService userService1, UserReservationService reservationService, MovieSessionService movieSessionService) {
        super(handler, client, userService);
        this.userService = userService1;
        this.reservationService = reservationService;
        this.movieSessionService = movieSessionService;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = user.getPsid();
        String cinemaId = (String) message.getPostback().getPayload();
        UserReservation userReservation = reservationService.findUserLatestReservation(psid);
        reservationService.saveCinema(Integer.parseInt(cinemaId), userReservation);
        int maxNumberOfTickets = this.movieSessionService.findMaxNumberOfTicketsForUserLastReservation(userReservation.getId());
        MessagingRequest request = new SelectNumberOfTicketsRequestBuilder(psid, maxNumberOfTickets).build();
        client.sandMassage(request);
        userService.save(psid, MessangerUserStatus.SELECT_NUMBER_OF_TICKETS);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_NEAREST_CINEMA;
    }
}
