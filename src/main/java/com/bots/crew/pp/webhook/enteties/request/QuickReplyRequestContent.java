package com.bots.crew.pp.webhook.enteties.request;

import com.bots.crew.pp.webhook.enteties.replies.QuickReply;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuickReplyRequestContent extends MessagingRequestContent {
    @JsonProperty("quick_replies")
    List<QuickReply> quickReplies;

    public List<QuickReply> getQuickReplies() {
        return quickReplies;
    }

    public void setQuickReplies(List<QuickReply> quickReplies) {
        this.quickReplies = quickReplies;
    }


}
