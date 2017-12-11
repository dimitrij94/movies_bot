package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.list_or_find.MoviesRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.FindOrListMoviesQuickRequestBuilder;
import com.bots.crew.pp.webhook.builders.quick.GanreQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.repositories.MovieGenreRepository;
import com.bots.crew.pp.webhook.repositories.MovieSessionRepository;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAllMoviesOrGenresObserver extends AbstractMessagingObserver {
    private MovieService movieService;
    private MovieGenreRepository genreRepository;
    private MovieSessionRepository sessionRepository;

    public ListAllMoviesOrGenresObserver(FacebookMessagingHandler handler,
                                         TextMessageClient client,
                                         MessengerUserService userService,
                                         MovieService movieService,
                                         MovieGenreRepository genreRepository) {
        super(handler, client, userService);
        this.movieService = movieService;
        this.genreRepository = genreRepository;
    }


    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        QuickReply quickReply = message.getMessage().getQuickReply();
        String replyPayload = (String) quickReply.getPayload();
        MessagingRequest request = null;
        MessangerUserStatus procidingStatus = null;
        if (replyPayload.equals(FindOrListMoviesQuickRequestBuilder.LIST_REPLY_PAYLOAD)) {
            List<Movie> todayMoviesList = movieService.findTodayMovies();
            if (todayMoviesList.isEmpty()) {
                ((TextMessageClient) client)
                        .sendTextMessage(psid, "Sorry it appears that there are no open tickets to book, that i am aware of =(");
            }
            MoviesRequestBuilder requestBuilder = new MoviesRequestBuilder(psid, todayMoviesList);
            request = requestBuilder.build();
            procidingStatus = MessangerUserStatus.SELECT_MOVIE;

        } else if (replyPayload.equals(FindOrListMoviesQuickRequestBuilder.FIND_REPLY_PAYLOAD)) {
            List<MovieGenre> movieGenres = this.genreRepository.findAll();
            request = new GanreQuickRequestBuilder(psid, movieGenres).build();
            procidingStatus = MessangerUserStatus.SELECT_GENRE;
        }

        this.client.sandMassage(request);
        this.userService.setStatus(psid, procidingStatus);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_METHOD_OF_FINDING_MOVIE;
    }
}