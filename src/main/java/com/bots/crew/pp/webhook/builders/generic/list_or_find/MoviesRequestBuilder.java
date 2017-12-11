package com.bots.crew.pp.webhook.builders.generic.list_or_find;

import com.bots.crew.pp.webhook.builders.GenericReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.LinkedList;
import java.util.List;

public class MoviesRequestBuilder extends GenericReplyBuilder {
    private List<Movie> movies;
    private ListAllMoviesRequestElementBuilder elementBuilder = new ListAllMoviesRequestElementBuilder();
    boolean includeBuyButton = true;

    public MoviesRequestBuilder(String psid, List<Movie> movies) {
        super(psid);
        this.movies = movies;
    }

    @Override
    protected List<GenericTamplateElement> getElements() {
        List<GenericTamplateElement> list = new LinkedList<>();
        for (Movie movie : movies) {
            list.add(elementBuilder.build(movie, includeBuyButton));
        }
        return list;
    }

    public void setIncludeBuyButton(boolean includeBuyButton) {
        this.includeBuyButton = includeBuyButton;
    }
}
