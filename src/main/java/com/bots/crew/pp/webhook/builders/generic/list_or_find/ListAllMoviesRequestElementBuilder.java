package com.bots.crew.pp.webhook.builders.generic.list_or_find;

import com.bots.crew.pp.webhook.builders.generic.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateButtons;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListAllMoviesRequestElementBuilder extends GenericReplyElementsBuilder {
    private Movie movie;
    boolean includeBuyTicketButton;

    public ListAllMoviesRequestElementBuilder() {
    }

    public GenericTamplateElement build(Movie movie, boolean includeBuyTicketButton) {
        this.setMovie(movie);
        this.includeBuyTicketButton = includeBuyTicketButton;
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
        List<GenericTamplateButtons> buttons = new LinkedList<>();
        buttons.add(viewTrailerButton());
        if (includeBuyTicketButton) buttons.add(buyTicketButton());
        return buttons;
    }

    private GenericTamplateButtons viewTrailerButton() {
        GenericTamplateButtons buttonOne = new GenericTamplateButtons();
        buttonOne.setTitle("Watch Trailer");
        buttonOne.setType("web_url");
        buttonOne.setUrl(movie.getTrailerUrl());
        return buttonOne;
    }

    private GenericTamplateButtons buyTicketButton() {
        GenericTamplateButtons buttonTwo = new GenericTamplateButtons();
        buttonTwo.setTitle("Buy Ticket");
        buttonTwo.setPayload(movie.getId().toString());
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
