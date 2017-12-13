package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.movie.MoviesRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieGenreService;
import com.bots.crew.pp.webhook.services.MovieService;
import com.bots.crew.pp.webhook.services.UserReservationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectGenreObserver extends AbstractMessagingObserver {
    private MovieService service;
    private MovieGenreService moviGenreService;
    private UserReservationService reservationService;

    public SelectGenreObserver(FacebookMessagingHandler handler,
                               TextMessageClient client,
                               MessengerUserService userService,
                               MovieService service,
                               MovieGenreService moviGenreService,
                               UserReservationService reservationService) {
        super(handler, client, userService);
        this.service = service;
        this.moviGenreService = moviGenreService;
        this.reservationService = reservationService;
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_GENRE;
    }

    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        MovieGenre genre = getGenre(message);
        if (genre == null) {
            return null;
        }
        reservation.setGenre(genre);
        return reservationService.save(reservation);
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        MessengerUser user = reservation.getUser();
        MovieGenre reservationGenre = reservation.getGenre();
        List<Movie> movieList = service.findByGenre(reservationGenre.getId());

        ((TextMessageClient) client).sendTextMessage(user.getPsid(),
                String.format("I'am also a big fan of %s movies. Check this list, I'm sure you will like it.",
                        moviGenreService.find(reservationGenre.getId()).getName()));
        MessagingRequest request = new MoviesRequestBuilder(user.getPsid(), movieList).build();
        this.client.sendMassage(request);
        this.userService.setStatus(user.getPsid(), MessangerUserStatus.SHOW_GENRE_MOVIES, getObservableStatus());
    }

    private MovieGenre getGenre(Messaging message) {
        QuickReply quickReply = message.getMessage().getQuickReply();
        if (quickReply != null) {
            return moviGenreService.find(Integer.parseInt((String) quickReply.getPayload()));
        }
        String replyName = message.getMessage().getText();
        if (replyName != null) {
            return moviGenreService.find(replyName);
        }
        return null;
    }


}
