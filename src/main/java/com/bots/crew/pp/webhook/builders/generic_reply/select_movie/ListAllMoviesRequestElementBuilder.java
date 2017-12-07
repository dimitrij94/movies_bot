package com.bots.crew.pp.webhook.builders.generic_reply.select_movie;

import com.bots.crew.pp.webhook.builders.generic_reply.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateButtons;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateElement;

import java.util.Arrays;
import java.util.List;

public class ListAllMoviesRequestElementBuilder extends GenericReplyElementsBuilder {
    private Movie movie;

    public ListAllMoviesRequestElementBuilder() {
    }

    public GenericTamplateElement build(Movie movie) {
        this.setMovie(movie);
        return super.build();
    }

    @Override
    protected String getTitle() {
        return movie.getTitle();
    }

    @Override
    protected String getSubtitle() {
        return null;
    }

    @Override
    protected String getImageUrl() {
        return movie.getImageUrl();
    }

    @Override
    protected List<GenericTamplateButtons> getGenericTamplateButtons() {
        return Arrays.asList(viewTrailerButton(), buyTicketButton());
    }

    private GenericTamplateButtons viewTrailerButton() {
        GenericTamplateButtons buttonOne = new GenericTamplateButtons();
        buttonOne.setTitle("Watch Trailer");
        buttonOne.setPayload(movie.getId().toString());
        buttonOne.setType("postback");
        return buttonOne;
    }

    private GenericTamplateButtons buyTicketButton() {
        GenericTamplateButtons buttonTwo = new GenericTamplateButtons();
        buttonTwo.setTitle("Buy Ticket");
        buttonTwo.setPayload("ticket_both:" + movie.getId().toString());
        buttonTwo.setType("postback");
        return buttonTwo;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
