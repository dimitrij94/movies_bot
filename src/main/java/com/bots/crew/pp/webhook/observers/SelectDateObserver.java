package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.quick.SelectCinemaRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Message;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SelectDateObserver extends AbstractMessagingObserver {
    private UserReservationService userReservationService;
    private CinemaService cinemaService;
    private Pattern datePattern = Pattern.compile("(?<month>[\\d]{1,2})-(?<day>[\\d]{1,2})-(?<year>[\\d]{4})");
    private MovieSessionService sessionService;

    public SelectDateObserver(FacebookMessagingHandler handler, TextMessageClient client, MessengerUserService userService, UserReservationService userReservationService, CinemaService cinemaService, MovieSessionService sessionService) throws IOException {
        super(handler, client, userService);
        this.userReservationService = userReservationService;
        this.cinemaService = cinemaService;
        this.sessionService = sessionService;

    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_SESSION_DATE;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        LocalDate userDate = getUserInput(message);
        if (userDate != null) {
            int numOfMovieSessionsAt = sessionService.countSessionsBySessionDate(userDate, reservation.getMovie());
            if (numOfMovieSessionsAt > 0) {
                return userReservationService.saveReservationForDate(reservation, UtilsService.convertToDate(userDate));
            }
            ((TextMessageClient) client).sendTextMessage(reservation.getUser().getPsid(),
                    "Sorry there are no movie sessions that i am aware of at this date. Please try another one");
            return null;
        } else {
            return null;
        }
    }


    @Override
    public void forwardResponse(UserReservation reservation) {
        List<Cinema> cinemas = cinemaService.findCinemasForMovieSessionAtDate(reservation.getMovie().getId(), reservation.getSessionDate());
        MessagingRequest request = new SelectCinemaRequestBuilder(reservation.getUser().getPsid(), cinemas).build();
        client.sendMassage(request);
        userService.setStatus(reservation.getUser(), MessangerUserStatus.SELECT_CINEMA_QUICK_LIST, getObservableStatus());
    }

    private LocalDate getUserInput(Messaging message) {
        Message m = message.getMessage();
        String psid = message.getSender().getId();
        QuickReply quickReply = m.getQuickReply();
        LocalDate localDate;
        if (quickReply != null) {
            localDate = convertUserPayload(quickReply);
        } else {
            localDate = convertUserInout(m.getText(), psid);
        }
        if (localDate == null || !validateUserInout(localDate, psid)) {
            return null;
        }
        return localDate;
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
        ((TextMessageClient) client).sendTextMessage(psid, "Sorry i am a movie tickets booking bot, not a time travel machine. " +
                "Select a date that is in the future");
        return false;
    }

    private LocalDate convertUserInout(String userDate, String psid) {
        Matcher matcher = datePattern.matcher(userDate);
        if (matcher.matches()) {
            try {
                return LocalDate.of(
                        Integer.parseInt(matcher.group("year")),
                        Integer.parseInt(matcher.group("month")),
                        Integer.parseInt(matcher.group("day"))
                );
            } catch (DateTimeException e) {
                ((TextMessageClient) client).sendTextMessage(psid, "This is not valid date, please check with your calender.");
                return null;
            }
        }
        return null;
    }


}
