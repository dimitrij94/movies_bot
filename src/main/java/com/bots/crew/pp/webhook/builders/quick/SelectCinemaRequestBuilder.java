package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.ArrayList;
import java.util.List;

public class SelectCinemaRequestBuilder extends QuickReplyBuilder {
    private String recipientId;
    private List<Cinema> cinemas;

    public SelectCinemaRequestBuilder(String recipientId, List<Cinema> cinemas) {
        this.recipientId = recipientId;
        this.cinemas = cinemas;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(recipientId);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("Great, what cinema would you prefer?");
        List<QuickReply> replyList = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            QuickReply quickReply = new QuickReply(cinema.getName(), cinema.getId().toString());
            replyList.add(quickReply);
        }
        replyList.add(new QuickReply("Find closest", "find"));
        requestContent.setQuickReplies(replyList);
        return requestContent;
    }
}
