package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LocatedCinemaGenericReplyBuilder extends CinemaGenericReplyBuilder {
    private Map<Integer, GoogleMatrixApiMessage> request;
    private LocatedCinemaGenericReplyElementsBuilder builder = new LocatedCinemaGenericReplyElementsBuilder();

    public LocatedCinemaGenericReplyBuilder(String psid, List<Cinema> cinemas, Map<Integer, GoogleMatrixApiMessage> request) {
        super(psid, cinemas);
        this.request = request;
    }

    @Override
    public MessagingRequest build() {
        return super.build();
    }

    @Override
    protected List<GenericTamplateElement> getElements() {
        List<GenericTamplateElement> elements = new LinkedList<>();
        for (Cinema c : super.cinemas) {
            elements.add(builder.build(c, request.get(c.getId())));
        }
        return elements;
    }

}
