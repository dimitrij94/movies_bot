package com.bots.crew.pp.webhook.enteties.in.messages;

import com.bots.crew.pp.webhook.enteties.replies.QuickReply;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageWithQuickReplies extends Message {
    @JsonProperty("quick_reply")
    private QuickReply reply;

    public QuickReply getReply() {
        return reply;
    }

    public void setReply(QuickReply reply) {
        this.reply = reply;
    }

}
