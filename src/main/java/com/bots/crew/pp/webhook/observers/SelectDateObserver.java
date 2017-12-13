package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectCinemaRequestBuilder;
import com.bots.crew.pp.webhook.client.MessageClient;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Message;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.CinemaService;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import com.bots.crew.pp.webhook.services.UtilsService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SelectDateObserver extends AbstractMessagingObserver {
    private UserReservationService userReservationService;
    private CinemaService cinemaService;
    private Pattern datePattern = Pattern.compile("(?<month>[\\d]{2})-(?<day>[\\d]{2})-(?<year>[\\d]{4})");

    public SelectDateObserver(FacebookMessagingHandler handler, TextMessageClient client, MessengerUserService userService, UserReservationService userReservationService, CinemaService cinemaService) {
        super(handler, client, userService);
        this.userReservationService = userReservationService;
        this.cinemaService = cinemaService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_SESSION_DATE;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        Date userDate = getUserInput(message);
        if (userDate == null) {
            return null;
        } else {
            return userReservationService.saveReservationForDate(reservation, userDate);
        }
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        List<Cinema> cinemas = cinemaService.findCinemasForMovie(reservation.getMovie().getId());
        MessagingRequest request = new SelectCinemaRequestBuilder(reservation.getUser().getPsid(), cinemas).build();
        client.sendMassage(request);userService.setStatus(reservation.getUser(), MessangerUserStatus.SELECT_CINEMA_QUICK_LIST, getObservableStatus());
    }

    private Date getUserInput(Messaging message) {
        Message m = message.getMessage();
        String psid = message.getSender().getId();
        QuickReply quickReply = m.getQuickReply();
        Date userDate;
        LocalDate localDate;
        if (quickReply != null) {
            localDate = convertUserPayload(quickReply);
        } else {
            localDate = convertUserInout(m.getText());
        }
        if (localDate == null || !validateUserInout(localDate, psid)) {
            return null;
        }
        userDate = UtilsService.convertToDate(localDate);
        return userDate;
    }


    private LocalDate convertUserPayload(QuickReply quickReply) {
        int dayOfTheMonth = Integer.parseInt((String) quickReply.getPayload());
        LocalDate today = LocalDate.now();
        return LocalDate.of(today.getYear(), today.getMonth(), dayOfTheMonth);
    }

    private boolean validateUserInout(LocalDate userDate, String psid) {
        if (userDate.isAfter(LocalDate.now())) {
            return true;
        }
        ((TextMessageClient) client).sendTextMessage(psid, "Sorry i am movie tickets booking bot, not a time travel machine.");
        return false;
    }

    private LocalDate convertUserInout(String userDate) {
        Matcher matcher = datePattern.matcher(userDate);
        if (matcher.matches()) {
            return LocalDate.of(
                    Integer.parseInt(matcher.group("year")),
                    Integer.parseInt(matcher.group("month")),
                    Integer.parseInt(matcher.group("day"))
            );
        }
        return null;
    }


}
