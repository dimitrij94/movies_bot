package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.list_or_find.MoviesRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.GanreQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.repositories.MovieGenreRepository;
import com.bots.crew.pp.webhook.services.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SelectListOrFindMovies extends AbstractMessagingObserver {
    private MovieService movieService;
    private MovieGenreRepository genreRepository;
    private UserReservationService userReservationService;
    private MovieSessionService sessionService;

    public SelectListOrFindMovies(FacebookMessagingHandler handler,
                                  TextMessageClient client,
                                  MessengerUserService userService,
                                  MovieService movieService,
                                  MovieGenreRepository genreRepository, UserReservationService userReservationService, MovieSessionService sessionService) {
        super(handler, client, userService);
        this.movieService = movieService;
        this.genreRepository = genreRepository;
        this.userReservationService = userReservationService;
        this.sessionService = sessionService;
    }


    @Override
    public UserReservation changeState(Messaging message, UserReservation reservation) {
        QuickReply quickReply = message.getMessage().getQuickReply();

        MessengerUser user = reservation.getUser();
        String replyPayload;

        if (quickReply != null) replyPayload = (String) quickReply.getPayload();
        else replyPayload = message.getMessage().getText().toLowerCase();

        if (replyPayload.equals("list")) {
            if (sessionService.countSessionsToday(
                    UtilsService.convertToDate(LocalDate.now())) > 0) {
                reservation = userReservationService.saveReservationForToday(reservation, user);
                return reservation;
            } else {
                ((TextMessageClient) client).sendTextMessage(user.getPsid(), "Sorry it seams there are no movie sessions left for today");
                return null;
            }

        } else if (replyPayload.equals("find")) {
            return reservation;
        }
        return null;
    }

    @Override
    public void forwardResponse(UserReservation reservation) {
        MessengerUser user = reservation.getUser();
        String psid = user.getPsid();
        if (reservation.getSessionDate() != null) {
            List<Movie> todayMoviesList = movieService.findTodayMovies();
            client.sendMassage(getTodayMoviesListRequest(user.getPsid(), todayMoviesList));
            this.userService.setStatus(reservation.getUser().getPsid(), MessangerUserStatus.SHOW_MOVIES, getObservableStatus());
        } else {
            List<MovieGenre> movieGenres = this.genreRepository.finAllWithKnownMovies();
            MessagingRequest request = new GanreQuickRequestBuilder(psid, movieGenres).build();
            client.sendMassage(request);
            this.userService.setStatus(psid, MessangerUserStatus.SELECT_GENRE, getObservableStatus());
        }
    }


    private MessagingRequest getTodayMoviesListRequest(String psid, List<Movie> todayMoviesList) {
        MessagingRequest request = null;
        if (todayMoviesList.isEmpty()) {
            ((TextMessageClient) client)
                    .sendTextMessage(psid, "Sorry it appears that there are no open tickets to book, that i am aware of =(");
        } else {
            ((TextMessageClient) client).sendTextMessage(psid, "Damn, what a hot list!");
            request = new MoviesRequestBuilder(psid, todayMoviesList).build();
        }
        return request;
    }

    /*
        private MessagingRequest getFindMoviesRequest(String psid, List<MovieGenre> movieGenres) {
            return new GanreQuickRequestBuilder(psid, movieGenres).build();
        }

        private UserReservation parseRequest(Messaging message, MessengerUser user) throws IllegalAccessException {
            QuickReply quickReply = message.getMessage().getQuickReply();
            String replyPayload;

            if (quickReply != null) replyPayload = (String) quickReply.getPayload();
            else replyPayload = message.getMessage().getText().toLowerCase();

            if (replyPayload.equals("list")) {
                List<Movie> todayMoviesList = movieService.findTodayMovies();
                UserReservation u = caseListMovies(user, todayMoviesList);
                client.sendMassage(getTodayMoviesListRequest(user.getPsid(), todayMoviesList));
                return u;
            } else if (replyPayload.equals("find")) {
                List<MovieGenre> movieGenres = this.genreRepository.finAllWithKnownMovies();
                UserReservation u = caseFindMovie(user, movieGenres);
                client.sendMassage(getFindMoviesRequest(user.getPsid(), movieGenres));
                return u;
            }
            throw new IllegalArgumentException();
        }

        private UserReservation caseFindMovie(MessengerUser user, List<MovieGenre> movieGenres) {
            String psid = user.getPsid();
            MessagingRequest request = new GanreQuickRequestBuilder(psid, movieGenres).build();
            this.userService.setStatus(psid, MessangerUserStatus.SELECT_GENRE);
            return this.userReservationService.createEmptyReservation(user);
        }

        private UserReservation caseListMovies(MessengerUser user, List<Movie> todayMoviesList) throws IllegalAccessException {
            String psid = user.getPsid();
            UserReservation reservation;
            if (todayMoviesList.isEmpty()) {
                ((TextMessageClient) client)
                        .sendTextMessage(psid, "Sorry it appears that there are no open tickets to book, that i am aware of =(");
                return null;
            } else {
                reservation = userReservationService.saveReservationForToday(psid);
                this.userService.setStatus(psid, MessangerUserStatus.SHOW_MOVIES);
            }
            return reservation;
        }

    */
    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_LIST_OR_FIND;
    }
}