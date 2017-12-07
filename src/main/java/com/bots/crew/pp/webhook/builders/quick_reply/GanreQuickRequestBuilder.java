package com.bots.crew.pp.webhook.builders.quick_reply;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.replies.QuickReply;
import com.bots.crew.pp.webhook.enteties.replies.TextQuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.List;
import java.util.stream.Collectors;

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
        requestContent.setText("What would you like to check first?");
        List<QuickReply> replyList = movieGenres
                .stream()
                .map((MovieGenre genre) -> new TextQuickReply(genre.getName(), genre.getId().toString()))
                .collect(Collectors.toList());

        requestContent.setQuickReplies(replyList);

        return requestContent;
    }
}
