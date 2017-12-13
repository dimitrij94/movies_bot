package com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply;

import com.bots.crew.pp.webhook.builders.generic.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateButton;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.Arrays;
import java.util.List;

public class CinemaGenericReplyElementsBuilder extends GenericReplyElementsBuilder {
    private Cinema cinema;

    public GenericTamplateElement build(Cinema cinema) {
        this.setCinema(cinema);
        return super.build();
    }

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
    protected List<GenericTamplateButton> getGenericTamplateButtons() {
        return Arrays.asList(getSelectCinemaButton(cinema), getShowLocationButton(cinema));
    }

    public static GenericTamplateButton getSelectCinemaButton(Cinema cinema) {
        GenericTamplateButton buttonTwo = new GenericTamplateButton();
        buttonTwo.setTitle("Select cinema");
        buttonTwo.setPayload(cinema.getId().toString());
        buttonTwo.setType("postback");
        return buttonTwo;
    }

    public static GenericTamplateButton getShowLocationButton(Cinema cinema) {
        GenericTamplateButton buttonOne = new GenericTamplateButton();
        buttonOne.setTitle("Show location");
        buttonOne.setType("web_url");
        buttonOne.setUrl(String.format("https://www.google.com/maps?q=%s", cinema.getAddress()));
        return buttonOne;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
