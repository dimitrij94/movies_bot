package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.builders.GenericReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.LinkedList;
import java.util.List;

public class CinemaGenericReplyBuilder extends GenericReplyBuilder {
    protected List<Cinema> cinemas;
    private CinemaGenericReplyElementsBuilder elementsBuilder;

    public CinemaGenericReplyBuilder(String psid, List<Cinema> cinemas) {
        super(psid);
        this.cinemas = cinemas;
        elementsBuilder = new CinemaGenericReplyElementsBuilder();
    }


    @Override
    protected List<GenericTamplateElement> getElements() {
        List<GenericTamplateElement> elements = new LinkedList<>();
        for (Cinema cinema : cinemas) {
            elements.add(elementsBuilder.build(cinema));
        }
        return elements;
    }

}
