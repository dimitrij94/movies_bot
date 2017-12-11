package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectCinemaRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyTicketForMovieObserver extends AbstractMessagingObserver {
    private CinemaService cinemaService;
    private UserReservationService reservationService;

    public BuyTicketForMovieObserver(FacebookMessagingHandler handler,
                                     MessageClient client,
                                     MessengerUserService userService, CinemaService cinemaService, UserReservationService reservationService) {
        super(handler, client, userService);
        this.cinemaService = cinemaService;
        this.reservationService = reservationService;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        String movieId = (String) message.getPostback().getPayload();
        reservationService.saveForMovie(Integer.parseInt(movieId), psid);
        List<Cinema> cinemas = cinemaService.findCinemasForMovie(Integer.parseInt(movieId));
        MessagingRequest request = new SelectCinemaRequestBuilder(psid, cinemas).build();
        client.sandMassage(request);
        userService.setStatus(psid, MessangerUserStatus.SELECT_CINEMA_QUICK_LIST);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_MOVIE;
    }
}
