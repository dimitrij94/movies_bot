package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiRequest;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiRequestElements;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;

public class LocatedCinemaGenericReplyElementsBuilder extends CinemaGenericReplyElementsBuilder {
    private GoogleMatrixApiRequest request;

    public LocatedCinemaGenericReplyElementsBuilder(GoogleMatrixApiRequest request) {
        this.request = request;
    }

    @Override
    protected String getSubtitle() {
        GoogleMatrixApiRequestElements respo = request.getRows().get(0).getElements().get(0);
        return super.getSubtitle() + " " + respo.getDistance().getText() + " " + respo.getDuration().getText();
    }
}
