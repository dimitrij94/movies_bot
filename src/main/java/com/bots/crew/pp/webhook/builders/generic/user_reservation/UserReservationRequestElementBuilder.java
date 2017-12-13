package com.bots.crew.pp.webhook.builders.generic.user_reservation;

import com.bots.crew.pp.webhook.builders.generic.GenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.builders.generic.cinema_genric_reply.CinemaGenericReplyElementsBuilder;
import com.bots.crew.pp.webhook.builders.generic.list_or_find.MoviesRequestElementBuilder;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateButton;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;
import com.bots.crew.pp.webhook.services.UtilsService;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class UserReservationRequestElementBuilder extends GenericReplyElementsBuilder {
    private UserReservation userReservation;


    public GenericTamplateElement build(UserReservation userReservation) {
        this.userReservation = userReservation;
        return super.build();
    }

    @Override
    protected String getTitle() {
        return userReservation.getCinema().getName() + ", " + userReservation.getMovie().getTitle();
    }

    @Override
    protected String getSubtitle() {
        return "At: " + UtilsService.convertToString(userReservation.getSession().getSessionTime());
    }

    @Override
    protected String getImageUrl() {
        return userReservation.getMovie().getImageUrl();
    }

    @Override
    protected List<GenericTamplateButton> getGenericTamplateButtons() {
        List<GenericTamplateButton>buttons = new LinkedList<>();
        buttons.add(CinemaGenericReplyElementsBuilder.getShowLocationButton(userReservation.getCinema()));
        buttons.add(MoviesRequestElementBuilder.viewTrailerButton(userReservation.getMovie()));
        return buttons;
    }
}
