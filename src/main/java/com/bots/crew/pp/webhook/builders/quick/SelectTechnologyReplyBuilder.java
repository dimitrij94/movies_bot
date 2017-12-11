package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.LinkedList;
import java.util.List;

public class SelectTechnologyReplyBuilder extends QuickReplyBuilder {
    private String psid;
    private List<MovieTechnology> technologies;

    public SelectTechnologyReplyBuilder(String psid, List<MovieTechnology> technologies) {
        this.psid = psid;
        this.technologies = technologies;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("Great! Now how would you prefer to watch this movie?");
        List<QuickReply> requestPayload = new LinkedList<>();
        requestPayload.add(new QuickReply("Any", "any"));
        for (MovieTechnology tech : technologies)
            requestPayload.add(new QuickReply(tech.getName(), tech.getId()));
        requestContent.setQuickReplies(requestPayload);
        return requestContent;
    }
}
