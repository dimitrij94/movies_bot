package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.Arrays;
import java.util.List;

public class FindOrListMoviesQuickRequestBuilder extends QuickReplyBuilder {
    private String recipientId;
    public final static String FIND_REPLY_PAYLOAD = "find";
    public final static String LIST_REPLY_PAYLOAD = "list";

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
                new QuickReply("Today`s list", LIST_REPLY_PAYLOAD),
                new QuickReply("Find a movie", FIND_REPLY_PAYLOAD));

        requestContent.setQuickReplies(replyList);
        return requestContent;
    }
}
