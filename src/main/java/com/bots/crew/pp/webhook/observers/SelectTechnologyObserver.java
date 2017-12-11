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
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.MovieTechnologyService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

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
    public void notify(Messaging message, MessengerUser user) {
        int technologyId = Integer.parseInt((String) message.getMessage().getQuickReply().getPayload());
        MovieTechnology selectedTechnology = technologyService.find(technologyId);
        UserReservation reservation = userReservationService.findUserLatestReservation(user.getPsid());
        List<MovieSession> sessions = sessionService
                .findSessionsForMovieCinemaAndTechnology(reservation.getId(), selectedTechnology.getId());

        MessagingRequest request = new SelectTimeQuickRequestBuilder(user, sessions).build();
        client.sandMassage(request);
        userService.setStatus(user.getPsid(), MessangerUserStatus.SELECT_TIME);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_TECHNOLOGY;
    }
}
