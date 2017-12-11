package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.builders.generic.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateButtons;

import java.util.Arrays;
import java.util.List;

public class CinemaGenericReplyElementsBuilder extends GenericReplyElementsBuilder {
    private Cinema cinema;

    @Override
    protected String getTitle() {
        return cinema.getName();
    }

    @Override
    protected String getSubtitle() {
        return cinema.getAddress();
    }

    @Override
    protected String getImageUrl() {
        return cinema.getImageUrl();
    }

    @Override
    protected List<GenericTamplateButtons> getGenericTamplateButtons() {
        return Arrays.asList(getSelectCinemaButton(), getShowLocationButton());
    }

    private GenericTamplateButtons getSelectCinemaButton() {
        GenericTamplateButtons buttonTwo = new GenericTamplateButtons();
        buttonTwo.setTitle("Select cinema");
        buttonTwo.setPayload(cinema.getId().toString());
        buttonTwo.setType("postback");
        return buttonTwo;
    }

    private GenericTamplateButtons getShowLocationButton() {
        GenericTamplateButtons buttonOne = new GenericTamplateButtons();
        buttonOne.setTitle("Show location");
        buttonOne.setType("web_url");
        buttonOne.setUrl(String.format("https://www.google.com/maps?q=%f,%f", cinema.getLongitude(), cinema.getLatitude()));
        return buttonOne;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
