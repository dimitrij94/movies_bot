package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectTechnologyReplyBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Message;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.MovieTechnologyService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectNumberOfTicketsObserver extends AbstractMessagingObserver {
    private UserReservationService reservationService;
    private MovieTechnologyService technologyService;
    private MovieSessionService movieSessionService;

    public SelectNumberOfTicketsObserver(FacebookMessagingHandler handler,
                                         TextMessageClient client,
                                         MessengerUserService userService,
                                         UserReservationService reservationService,
                                         MovieTechnologyService technologyService,
                                         MovieSessionService movieSessionService) {
        super(handler, client, userService);
        this.reservationService = reservationService;
        this.technologyService = technologyService;
        this.movieSessionService = movieSessionService;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        MessengerUser user = reservation.getUser();
        UserReservation userReservation = parseRequest(reservation, message);
        if (userReservation == null) {
            ((TextMessageClient) client).sendTextMessage(user.getPsid(),
                    "Sorry i cannot book this amount of tickets, please try another number.");
        }
        return userReservation;
    }

    @Override
    public void forwardResponse(UserReservation userReservation) {
        MessengerUser user = userReservation.getUser();
        List<MovieTechnology> technologies = technologyService.getAvailableTechnologies(userReservation.getId());
        MessagingRequest request = new SelectTechnologyReplyBuilder(user.getPsid(), technologies).build();
        client.sendMassage(request);
        userService.setStatus(user, MessangerUserStatus.SELECT_TECHNOLOGY, getObservableStatus());
    }

    private UserReservation parseRequest(UserReservation reservation, Messaging message) {
        Integer numOfTickets = parseOutNumOfTickets(message);
        if (numOfTickets == null) return null;
        int maxNumberOfTickets = this.movieSessionService.findMaxNumberOfTicketsForUserLastReservation(reservation.getId());
        if (numOfTickets <= maxNumberOfTickets && numOfTickets > 0) {
            reservationService.saveNumberOfTickets(reservation, maxNumberOfTickets);
            return reservation;
        } else {
            return null;
        }
    }

    private Integer parseOutNumOfTickets(Messaging message) {
        Message messageContent = message.getMessage();
        QuickReply reply = messageContent.getQuickReply();
        try {
            if (reply == null) {
                return Integer.parseInt(messageContent.getText());
            } else {
                return Integer.parseInt((String) reply.getPayload());
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_NUMBER_OF_TICKETS;
    }
}
