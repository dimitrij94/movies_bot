package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.Collections;

public class GettingStartedQuickReplyBuilder extends QuickReplyBuilder {
    private String psid;

    public GettingStartedQuickReplyBuilder(String psid) {
        this.psid = psid;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        requestContent.setText("Wold you like to book another ticket?");
        requestContent.setQuickReplies(Collections.singletonList(
                new QuickReply("Getting Started", "getting started")));
        return requestContent;
    }
}
