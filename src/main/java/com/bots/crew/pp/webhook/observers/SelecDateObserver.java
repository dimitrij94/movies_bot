package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectCinemaRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Message;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SelecDateObserver extends AbstractMessagingObserver {
    private UserReservationService userReservationService;
    private CinemaService cinemaService;
    public SelecDateObserver(FacebookMessagingHandler handler, MessageClient client, MessengerUserService userService, UserReservationService userReservationService, CinemaService cinemaService) {
        super(handler, client, userService);
        this.userReservationService = userReservationService;
        this.cinemaService = cinemaService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_SESSION_DATE;
    }

    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        Message m = message.getMessage();
        QuickReply quickReply = m.getQuickReply();
        String payload;
        if(quickReply!=null){
            payload = (String) quickReply.getPayload();
        }
        else {
            payload = null;
        }
        int dayOfTheMonth = Integer.parseInt(payload);
        UserReservation userLastReservation = userReservationService.findUserLatestReservation(psid);
        LocalDate today = LocalDate.now();
        LocalDate localDate = LocalDate.of(today.getYear(), today.getMonth(), dayOfTheMonth);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        userLastReservation.setSessionDate(date);
        userReservationService.saveReservationForToday(userLastReservation);

        List<Cinema> cinemas = cinemaService.findCinemasForMovie(userLastReservation.getMovie().getId());
        MessagingRequest request = new SelectCinemaRequestBuilder(psid, cinemas).build();
        client.sendMassage(request);
        userService.setStatus(psid, MessangerUserStatus.SELECT_CINEMA_QUICK_LIST);
    }

    private boolean validateUserInput(){
        return false;
    }
}
