package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.Collections;

public class SendLocationRequestBuilder extends QuickReplyBuilder {
    private String psid;
    private String message;

    public SendLocationRequestBuilder(String psid, String message) {
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
        QuickReply locationReply = new QuickReply();
        locationReply.setContentType("location");
        requestContent.setQuickReplies(Collections.singletonList(locationReply));
        return requestContent;
    }


}
