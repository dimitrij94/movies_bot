package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.DayOfTheSessionBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.messages.Postback;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

@Component
public class ShowMoviesOfGenreObserver extends AbstractMessagingObserver {
    private UserReservationService reservationService;

    public ShowMoviesOfGenreObserver(FacebookMessagingHandler handler, TextMessageClient client, MessengerUserService userService, UserReservationService reservationService) {
        super(handler, client, userService);
        this.reservationService = reservationService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SHOW_GENRE_MOVIES;
    }


    private void sendRequest(UserReservation userReservation) {
        MessagingRequest req = new DayOfTheSessionBuilder(userReservation.getUser().getPsid()).build();
        client.sendMassage(req);
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation userReservation) {
        Postback postback = message.getPostback();
        if (postback != null) {
            int movieId = Integer.parseInt((String) postback.getPayload());
            return reservationService.updateSetMovie(userReservation, movieId);
        }
        return null;
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        String psid = reservation.getUser().getPsid();
        ((TextMessageClient) client).sendTextMessage(psid, "Great choice, i realy like this movie.");
        sendRequest(reservation);
        userService.setStatus(psid, MessangerUserStatus.SELECT_SESSION_DATE, getObservableStatus());

    }
}
