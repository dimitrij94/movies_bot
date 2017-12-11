package com.bots.crew.pp.webhook.builders.text;

import com.bots.crew.pp.webhook.builders.TextReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequestContent;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

public class TextReplyBuilderImpl extends TextReplyBuilder {
    private String psid;
    private String message;

    public TextReplyBuilderImpl() {
    }

    public TextReplyBuilderImpl(String psid, String message) {
        this.psid = psid;
        this.message = message;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected MessagingRequestContent getRequestContent() {
        MessagingRequestContent requestContent = new MessagingRequestContent();
        requestContent.setText(message);
        return requestContent;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getPsid() {
        return psid;
    }

    @Override
    public void setPsid(String psid) {
        this.psid = psid;
    }
}
