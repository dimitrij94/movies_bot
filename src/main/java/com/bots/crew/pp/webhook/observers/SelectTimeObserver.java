package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.builders.ReplyBuilder;
import com.bots.crew.pp.webhook.builders.generic.list_or_find.MoviesRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.GettingStartedQuickReplyBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Component
public class SelectTimeObserver extends AbstractMessagingObserver {
    private UserReservationService reservationService;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

    public SelectTimeObserver(FacebookMessagingHandler handler,
                              TextMessageClient client,
                              MessengerUserService userService,
                              UserReservationService reservationService) {
        super(handler, client, userService);
        this.reservationService = reservationService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_TIME;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = user.getPsid();
            int sessionId = Integer.parseInt((String) message.getMessage().getQuickReply().getPayload());
        UserReservation reservation = reservationService.findUserLatestReservation(psid);
        reservation = reservationService.updateForMovieSession(reservation, sessionId);
        List<Movie> movie = Collections.singletonList(reservation.getMovie());
        ReplyBuilder builder;

        builder = new MoviesRequestBuilder(psid, movie);
        ((MoviesRequestBuilder) builder).setIncludeBuyButton(false);
        ((TextMessageClient) client).sendTextMessage(psid,
                String.format("Great, so I've booked %d tickets for you. \"%s\" in %s at %s.\n" +
                                "Tickets will be waiting for you in the ticket office.",
                        reservation.getNumberOfTickets(),
                        reservation.getMovie().getTitle(),
                        reservation.getCinema().getName(),
                        dateFormat.format(reservation.getSession().getSessionTime())));

        MessagingRequest request = builder.build();
        client.sendMassage(request);
        reservation.setActivated(true);
        reservationService.saveReservationForToday(reservation);
        userService.setStatus(psid, MessangerUserStatus.GETTING_STARTED);
        QuickReplyBuilder gettingStartedBuilder = new GettingStartedQuickReplyBuilder(psid, GettingStartedQuickReplyBuilder.GREETING_MESSAGE);
        client.sendMassage(gettingStartedBuilder.build());
    }
}
