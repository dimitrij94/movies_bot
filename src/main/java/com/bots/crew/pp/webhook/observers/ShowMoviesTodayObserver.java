package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectCinemaRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.messages.Postback;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowMoviesTodayObserver extends AbstractMessagingObserver {
    private CinemaService cinemaService;
    private UserReservationService reservationService;

    public ShowMoviesTodayObserver(FacebookMessagingHandler handler,
                                   TextMessageClient client,
                                   MessengerUserService userService, CinemaService cinemaService, UserReservationService reservationService) {
        super(handler, client, userService);
        this.cinemaService = cinemaService;
        this.reservationService = reservationService;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        Postback postback = message.getPostback();
        if (postback != null) {
            String movieId = (String) postback.getPayload();
            reservation = reservationService.updateForMovie(Integer.parseInt(movieId), reservation);
            return reservation;
        }
        return null;
    }

    @Override
    public void forwardResponse(UserReservation userReservation) {
        ((TextMessageClient) client).sendTextMessage(userReservation.getUser().getPsid(), "Great choice, i realy like this movie.");
        List<Cinema> cinemas = cinemaService.findCinemasForMovieToday(userReservation.getMovie().getId());
        MessagingRequest request = new SelectCinemaRequestBuilder(userReservation.getUser().getPsid(), cinemas).build();
        client.sendMassage(request);
        this.userService.setStatus(userReservation.getUser(), MessangerUserStatus.SELECT_CINEMA_QUICK_LIST, getObservableStatus());
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SHOW_MOVIES;
    }
}
