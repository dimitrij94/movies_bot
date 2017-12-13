package com.bots.crew.pp.webhook.builders.generic.cinema;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiRequestElements;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

public class LocatedCinemaGenericReplyElementsBuilder extends CinemaGenericReplyElementsBuilder {
    private GoogleMatrixApiMessage message;
    private String subtitleTemplate = "%s; %n It is located %s from you, it will take %s to get there";

    public GenericTamplateElement build(Cinema cinema, GoogleMatrixApiMessage message) {
        this.setMessage(message);
        return super.build(cinema);
    }

    @Override
    protected String getSubtitle() {
        GoogleMatrixApiRequestElements elem = message.getRows().get(0).getElements().get(0);
        return String.format(subtitleTemplate, super.getSubtitle(), elem.getDuration().getText(), elem.getDistance().getText());
    }

    public void setMessage(GoogleMatrixApiMessage message) {
        this.message = message;
    }
}
