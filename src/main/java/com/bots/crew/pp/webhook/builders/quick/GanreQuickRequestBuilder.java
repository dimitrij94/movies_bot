package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.LinkedList;
import java.util.List;

public class GanreQuickRequestBuilder extends QuickReplyBuilder {

    private String recipientId;
    private List<MovieGenre> movieGenres;

    public GanreQuickRequestBuilder(String recipientId, List<MovieGenre> movieGenres) {
        this.recipientId = recipientId;
        this.movieGenres = movieGenres;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(recipientId);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("What genre do you want to watch?");
        List<QuickReply> replyList = new LinkedList<>();
        for (MovieGenre genre : movieGenres) {
            replyList.add(new QuickReply(genre.getName(), genre.getId().toString()));
        }
        requestContent.setQuickReplies(replyList);
        return requestContent;
    }
}
