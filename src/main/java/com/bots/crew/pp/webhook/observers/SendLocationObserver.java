package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.cinema.LocatedCinemaGenericReplyBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.CinemaGoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendLocationObserver extends AbstractMessagingObserver {
    private CinemaService cinemaService;
    private MessengerUserService userService;
    private UserReservationService reservationService;

    public SendLocationObserver(FacebookMessagingHandler handler,
                                TextMessageClient client,
                                MessengerUserService userService,
                                CinemaService cinemaService,
                                MessengerUserService userService1, UserReservationService reservationService) {
        super(handler, client, userService);
        this.cinemaService = cinemaService;
        this.userService = userService1;
        this.reservationService = reservationService;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        try {
            CoordinatesPayload payload = message.getMessage().getAttachments().get(0).getPayload().getCoordinates();
            reservation.setLongitude(payload.getLongitude());
            reservation.setLatitude(payload.getLatitude());
            this.userService.setStatus(reservation.getUser(), MessangerUserStatus.SHOW_NEAREST_CINEMAS, getObservableStatus());
            return reservationService.save(reservation);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        String psid = reservation.getUser().getPsid();
        List<Cinema> cinemas = this.cinemaService.findCinemasForMovieToday(reservation.getMovie().getId());
        List<CinemaGoogleMatrixApiMessage> distances = this.cinemaService.findCinemasOrderByDistanceTo(cinemas,
                reservation.getLongitude(), reservation.getLatitude());

        ((TextMessageClient) client).sendTextMessage(psid,
                "Here is a list of the closest cinemas");

        MessagingRequest request = new LocatedCinemaGenericReplyBuilder(psid, distances).build();
        this.client.sendMassage(request);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.RQUEST_TO_SEND_LOCATION;
    }
}
