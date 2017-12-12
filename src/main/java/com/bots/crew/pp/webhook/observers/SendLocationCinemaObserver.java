package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply.CinemaGenericReplyBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendLocationCinemaObserver extends AbstractMessagingObserver {
    private CinemaService cinemaService;
    private MessengerUserService userService;

    public SendLocationCinemaObserver(FacebookMessagingHandler handler,
                                      TextMessageClient client,
                                      MessengerUserService userService,
                                      CinemaService cinemaService,
                                      MessengerUserService userService1) {
        super(handler, client, userService);
        this.cinemaService = cinemaService;
        this.userService = userService1;
    }


    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        CoordinatesPayload payload = message.getMessage().getAttachments().get(0).getPayload().getCoordinates();
        List<Cinema> cinemas = this.cinemaService.findCinemasOrderByDistanceTo(payload.getLongitude(), payload.getLatitude());
        ((TextMessageClient) client).sendTextMessage(psid,
                "Here is a list of the closest cinemas");
        MessagingRequest request = new CinemaGenericReplyBuilder(psid, cinemas).build();
        this.client.sendMassage(request);
        this.userService.save(psid, MessangerUserStatus.SELECT_NEAREST_CINEMA);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.RQUEST_TO_SEND_LOCATION;
    }
}
