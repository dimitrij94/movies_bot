package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.builders.generic.list_or_find.MoviesRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.FacebookMessagingHandler;
import com.bots.crew.pp.webhook.services.MessengerUserService;
import com.bots.crew.pp.webhook.services.MovieGenreService;
import com.bots.crew.pp.webhook.services.MovieService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListGenreMoviesObserver extends AbstractMessagingObserver {
    private MovieService service;
    private MovieGenreService moviGenreService;

    public ListGenreMoviesObserver(FacebookMessagingHandler handler,
                                   TextMessageClient client,
                                   MessengerUserService userService,
                                   MovieService service, MovieGenreService moviGenreService) {
        super(handler, client, userService);
        this.service = service;
        this.moviGenreService = moviGenreService;
    }


    @Override
    public void notify(Messaging message, MessengerUser user) {
        String psid = message.getSender().getId();
        int genreId = Integer.parseInt((String) message.getMessage().getQuickReply().getPayload());

        List<Movie> movieList = service.findByGenre(genreId);
        MessagingRequest request = new MoviesRequestBuilder(psid, movieList).build();

        ((TextMessageClient) client).sendTextMessage(psid,
                String.format("I'am also a big fan of %s movies. Check this list, I'm sure you will like it.", moviGenreService.find(genreId)));
        client.sendMassage(request);
        this.userService.setStatus(psid, MessangerUserStatus.SELECT_GENRE_MOVIE);
    }

    @Override
    public MessangerUserStatus getObservableStatus() {
        return MessangerUserStatus.SELECT_GENRE;
    }
}
