package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectNumberOfTicketsRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.messages.Postback;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

@Component
public class ShowCinemasObserver extends AbstractMessagingObserver {
    private MessengerUserService userService;
    private UserReservationService reservationService;
    private MovieSessionService movieSessionService;

    public ShowCinemasObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, MessengerUserService userService1, UserReservationService reservationService, MovieSessionService movieSessionService) {
        super(handler, client, userService);
        this.userService = userService1;
        this.reservationService = reservationService;
        this.movieSessionService = movieSessionService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SHOW_NEAREST_CINEMAS;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        Postback postback = message.getPostback();
        if (postback != null) {
            String cinemaId = (String) postback.getPayload();
            userService.setStatus(reservation.getUser(), MessangerUserStatus.SELECT_NUMBER_OF_TICKETS, getObservableStatus());
            return reservationService.saveCinema(Integer.parseInt(cinemaId), reservation);
        }
        return null;
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        int maxNumberOfTickets = this.movieSessionService.findMaxNumberOfTicketsForUserLastReservation(reservation.getId());
        MessagingRequest request = new SelectNumberOfTicketsRequestBuilder(reservation.getUser().getPsid(), maxNumberOfTickets).build();
        client.sendMassage(request);
    }
}
