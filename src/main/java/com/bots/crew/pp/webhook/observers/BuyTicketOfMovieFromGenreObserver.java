package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.DayOfTheSessionBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BuyTicketOfMovieFromGenreObserver extends AbstractMessagingObserver {
    private UserReservationService reservationService;

    public BuyTicketOfMovieFromGenreObserver(FacebookMessagingHandler handler, TextMessageClient client, MessengerUserService userService, UserReservationService reservationService) {
        super(handler, client, userService);
        this.reservationService = reservationService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_GENRE_MOVIE;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        int movieId = Integer.parseInt((String) message.getPostback().getPayload());
        ((TextMessageClient) client).sendTextMessage(psid, "Great choice, i realy like this movie.");

        UserReservation userReservation = reservationService.findUserLatestReservation(psid);
        if (userReservation == null) {
            userReservation = reservationService.createAndSetMovie(psid, movieId);
        }
        reservationService.updateForMovie(movieId, userReservation.getId());

        MessagingRequest req = new DayOfTheSessionBuilder(psid).build();
        client.sendMassage(req);
        userService.setStatus(psid, MessangerUserStatus.SELECT_SESSION_DATE);
    }
}
