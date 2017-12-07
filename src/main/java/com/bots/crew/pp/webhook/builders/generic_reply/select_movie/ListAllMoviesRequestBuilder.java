package com.bots.crew.pp.webhook.builders.generic_reply.select_movie;

import com.bots.crew.pp.webhook.builders.generic_reply.GenericReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateElement;

import java.util.LinkedList;
import java.util.List;

public class ListAllMoviesRequestBuilder extends GenericReplyBuilder {
    private List<Movie> movies;
    private ListAllMoviesRequestElementBuilder elementBuilder = new ListAllMoviesRequestElementBuilder();

    public ListAllMoviesRequestBuilder(String psid, List<Movie> movies) {
        super(psid);
        this.movies = movies;
    }


    @Override
    protected List<GenericTamplateElement> getElements() {
        List<GenericTamplateElement> list = new LinkedList<>();
        for (Movie movie : movies) {
            elementBuilder.build(movie);
        }
        return list;
    }
}
