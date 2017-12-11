package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiRequest;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;

public class LocatedCinemaGenericReplyElementsBuilder extends CinemaGenericReplyElementsBuilder {
    private GoogleMatrixApiRequest request;

    @Override
    protected String getSubtitle() {
        return super.getSubtitle()+" "+request.getRows().get(0).getElements();
    }
}
