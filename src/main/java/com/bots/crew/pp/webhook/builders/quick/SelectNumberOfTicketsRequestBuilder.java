package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.util.LinkedList;
import java.util.List;

public class SelectNumberOfTicketsRequestBuilder extends QuickReplyBuilder {
    private String psid;
    private int maxNumberOfTickets;

    public SelectNumberOfTicketsRequestBuilder(String psid, int maxNumberOfTickets) {
        this.psid = psid;
        this.maxNumberOfTickets = maxNumberOfTickets;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent content = new QuickReplyRequestContent();
        content.setText("How many tickets do you need? (Choose option below or type the number).");
        List<QuickReply> replies = new LinkedList<>();
        int max = maxNumberOfTickets > 10 ? 10 : maxNumberOfTickets;
        for (int i = 0; i < max; i++) {
            String ticketNumber = Integer.toString(i + 1);
            replies.add(new QuickReply(ticketNumber, i + 1));
        }
        content.setQuickReplies(replies);
        return content;
    }
}
