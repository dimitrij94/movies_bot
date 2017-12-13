package com.bots.crew.pp.webhook.builders.generic.cinema;

import com.bots.crew.pp.webhook.enteties.messages.CinemaGoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LocatedCinemaGenericReplyBuilder extends CinemaGenericReplyBuilder {
    private LocatedCinemaGenericReplyElementsBuilder builder = new LocatedCinemaGenericReplyElementsBuilder();
    List<CinemaGoogleMatrixApiMessage> messages;

    public LocatedCinemaGenericReplyBuilder(String psid, List<CinemaGoogleMatrixApiMessage> messages) {
        super(psid, messages.stream().map(CinemaGoogleMatrixApiMessage::getCinema).collect(Collectors.toList()));
        this.messages = messages;
    }

    @Override
    public MessagingRequest build() {
        return super.build();
    }

    @Override
    protected List<GenericTamplateElement> getElements() {
        List<GenericTamplateElement> elements = new LinkedList<>();
        for (CinemaGoogleMatrixApiMessage c : messages) {
            elements.add(builder.build(c.getCinema(), c.getDistanceTo()));
        }
        return elements;
    }

}
