package com.bots.crew.pp.webhook.enteties.messages;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;

public class CinemaGoogleMatrixApiMessage {
    private Cinema cinema;
    private GoogleMatrixApiMessage distanceTo;

    public CinemaGoogleMatrixApiMessage(Cinema cinema, GoogleMatrixApiMessage distanceTo) {
        this.cinema = cinema;
        this.distanceTo = distanceTo;
    }

    public CinemaGoogleMatrixApiMessage() {

    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public GoogleMatrixApiMessage getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(GoogleMatrixApiMessage distanceTo) {
        this.distanceTo = distanceTo;
    }
}
