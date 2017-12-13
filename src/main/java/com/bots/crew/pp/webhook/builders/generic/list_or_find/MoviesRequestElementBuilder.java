package com.bots.crew.pp.webhook.builders.generic.list_or_find;

import com.bots.crew.pp.webhook.builders.generic.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateButton;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.LinkedList;
import java.util.List;

public class MoviesRequestElementBuilder extends GenericReplyElementsBuilder {
    private Movie movie;
    boolean includeBuyTicketButton;

    public MoviesRequestElementBuilder() {
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
    protected List<GenericTamplateButton> getGenericTamplateButtons() {
        List<GenericTamplateButton> buttons = new LinkedList<>();
        buttons.add(viewTrailerButton(movie));
        if (includeBuyTicketButton) buttons.add(buyTicketButton(movie));
        return buttons;
    }

    public static GenericTamplateButton viewTrailerButton(Movie movie) {
        GenericTamplateButton buttonOne = new GenericTamplateButton();
        buttonOne.setTitle("Watch Trailer");
        buttonOne.setType("web_url");
        buttonOne.setUrl(movie.getTrailerUrl());
        return buttonOne;
    }

    public static GenericTamplateButton buyTicketButton(Movie movie) {
        GenericTamplateButton buttonTwo = new GenericTamplateButton();
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
