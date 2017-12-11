package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectTechnologyReplyBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieTechnologyService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectNumberOfTicketsObserver extends AbstractMessagingObserver {
    private MessengerUserService userService;
    private UserReservationService reservationService;
    private MovieTechnologyService technologyService;

    public SelectNumberOfTicketsObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, MessengerUserService userService1, UserReservationService reservationService, MovieTechnologyService technologyService) {
        super(handler, client, userService);
        this.userService = userService1;
        this.reservationService = reservationService;
        this.technologyService = technologyService;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        int numOfTickets = Integer.parseInt((String) message.getMessage().getQuickReply().getPayload());
        reservationService.saveNumberOfTickets(user.getPsid(), numOfTickets);
        UserReservation reservation = reservationService.findUserLatestReservation(user.getPsid());
        List<MovieTechnology> technologies = technologyService.getAvailableTechnologies(reservation.getId());
        MessagingRequest request = new SelectTechnologyReplyBuilder(user.getPsid(), technologies).build();
        client.sandMassage(request);
        userService.save(user.getPsid(), MessangerUserStatus.SELECT_TECHNOLOGY);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_NUMBER_OF_TICKETS;
    }
}
