package com.bots.crew.pp.webhook.builders.generic.reservation;

import com.bots.crew.pp.webhook.builders.GenericReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.enteties.request.GenericTamplateElement;

import java.util.List;
import java.util.stream.Collectors;

public class UserReservationdRequestBuilder extends GenericReplyBuilder {
    private List<UserReservation> userReservation;
    private UserReservationRequestElementBuilder elementBuilder = new UserReservationRequestElementBuilder();

    public UserReservationdRequestBuilder(String psid, List<UserReservation> userReservation) {
        super(psid);
        this.userReservation = userReservation;
    }

    @Override
    protected List<GenericTamplateElement> getElements() {
        return this.userReservation.stream()
                .map(elementBuilder::build)
                .collect(Collectors.toList());
    }
}
