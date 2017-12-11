package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.Collections;

public class GettingStartedQuickReplyBuilder extends QuickReplyBuilder {
    private String psid;
    public static final String GREETING_MESSAGE = "Hi, Dmitrij! \n" +
            "My name is Isaac The Cinema Bot. \n" +
            "I will help you choose a movie and book tickets";
    private String message;

    public GettingStartedQuickReplyBuilder(String psid, String message) {
        this.psid = psid;
        this.message = message;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText(message);
        requestContent.setQuickReplies(Collections.singletonList(
                new QuickReply("Getting Started", "getting started")));
        return requestContent;
    }
}
