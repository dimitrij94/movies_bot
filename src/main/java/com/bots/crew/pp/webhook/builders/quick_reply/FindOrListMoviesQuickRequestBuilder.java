package com.bots.crew.pp.webhook.builders.quick_reply;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.replies.QuickReply;
import com.bots.crew.pp.webhook.enteties.replies.TextQuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.Arrays;
import java.util.List;

public class FindOrListMoviesQuickRequestBuilder extends QuickReplyBuilder {
    private String recipientId;

    public FindOrListMoviesQuickRequestBuilder(String recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(recipientId);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("What would you like to check first?");
        List<QuickReply> replyList = Arrays.asList(
                new TextQuickReply("Today`s list", "list"),
                new TextQuickReply("Find a movie", "find"));

        requestContent.setQuickReplies(replyList);
        return requestContent;
    }
}
