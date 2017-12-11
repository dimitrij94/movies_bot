package com.bots.crew.pp.webhook.enteties.messages;

import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;

public class MessageAttachmentPayload {
    private CoordinatesPayload coordinates;

    public CoordinatesPayload getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesPayload coordinates) {
        this.coordinates = coordinates;
    }
}
