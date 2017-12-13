package com.bots.crew.pp.webhook.builders.quick;


import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;
import com.bots.crew.pp.webhook.services.UtilsService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SelectTimeQuickRequestBuilder extends QuickReplyBuilder {
    private MessengerUser user;
    private List<MovieSession> sessions;

    public SelectTimeQuickRequestBuilder(MessengerUser user, List<MovieSession> sessions) {
        this.user = user;
        this.sessions = sessions;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(user.getPsid());
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("Please select time of the movie session");
        List<QuickReply> sessionTimes = new LinkedList<>();
        sessionTimes.add(new QuickReply("Back","back"));
        for (MovieSession s : sessions) {
            Date sTime = s.getSessionTime();
            String time = UtilsService.convertToLocalTime(sTime).toString();
            sessionTimes.add(new QuickReply(time, s.getId()));
        }
        requestContent.setQuickReplies(sessionTimes);
        return requestContent;
    }


}
