package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectTimeQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SelectTechnologyObserver extends AbstractMessagingObserver {
    private MovieTechnologyService technologyService;
    private UserReservationService userReservationService;
    private MovieSessionService sessionService;

    public SelectTechnologyObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, MovieTechnologyService technologyService, UserReservationService userReservationService, MovieSessionService sessionService) {
        super(handler, client, userService);
        this.technologyService = technologyService;
        this.userReservationService = userReservationService;
        this.sessionService = sessionService;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        String input = parseTechnology(message);
        if (input.equals("any")) {
            return reservation;
        } else {
            try {
                int technologyId = Integer.parseInt(input);
                MovieTechnology selectedTechnology = technologyService.find(technologyId);
                reservation.setTechnology(selectedTechnology);
                return userReservationService.save(reservation);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private String parseTechnology(Messaging message) {
        QuickReply quickReply = message.getMessage().getQuickReply();
        return quickReply == null ? message.getMessage().getText() : (String) quickReply.getPayload();
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        List<MovieSession> sessions;
        if (reservation.getTechnology() == null) {
            sessions = sessionService.findMoviesSessionsWithoutTechnology(reservation);
        } else {
            sessions = sessionService
                    .findMoviesSessionsWithTechnology(reservation, reservation.getTechnology());
        }
        MessagingRequest request = new SelectTimeQuickRequestBuilder(reservation.getUser(), sessions).build();
        client.sendMassage(request);
        userService.setStatus(reservation.getUser(), MessangerUserStatus.SELECT_TIME, getObservableStatus());
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_TECHNOLOGY;
    }
}
