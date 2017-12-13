package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectNumberOfTicketsRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.SendLocationRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieSessionService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

@Component
public class SelectCinemaObserver extends AbstractMessagingObserver {
    private UserReservationService userReservationService;
    private MovieSessionService movieSessionService;

    public SelectCinemaObserver(FacebookMessagingHandler handler,
                                MessageClient client,
                                MessengerUserService userService,
                                UserReservationService userReservationService, MovieSessionService movieSessionService) {
        super(handler, client, userService);
        this.userReservationService = userReservationService;
        this.movieSessionService = movieSessionService;
    }


    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_CINEMA_QUICK_LIST;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        QuickReply quickReply = message.getMessage().getQuickReply();
        if (quickReply!= null) {
            String payload = (String) quickReply.getPayload();
            if (payload.equals("find")) {
                userService.setStatus(reservation.getUser(), MessangerUserStatus.RQUEST_TO_SEND_LOCATION, getObservableStatus());
                return reservation;
            } else {
                int cinemaId = Integer.parseInt(payload);
                userService.setStatus(reservation.getUser(), MessangerUserStatus.SELECT_NUMBER_OF_TICKETS, getObservableStatus());

                return userReservationService.saveCinema(cinemaId, reservation);
            }
        }
        return null;
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        MessengerUser u = reservation.getUser();
        MessagingRequest request = null;
        if (u.getStatus().equals(MessangerUserStatus.RQUEST_TO_SEND_LOCATION)) {
            request = new SendLocationRequestBuilder(u.getPsid()).build();
        } else if (u.getStatus().equals(MessangerUserStatus.SELECT_NUMBER_OF_TICKETS)) {
            int maxNumberOfTickets = movieSessionService.findMaxNumberOfTicketsForUserLastReservation(reservation.getId());
            request = new SelectNumberOfTicketsRequestBuilder(reservation.getUser().getPsid(), maxNumberOfTickets).build();
        }
        client.sendMassage(request);
    }
}
