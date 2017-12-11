package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SelectDateRequestBuilder extends QuickReplyBuilder {
    private String psid;

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent request = new QuickReplyRequestContent();
        request.setText("Alright, now, when do you plan to go the movie theatre? Select one of the options, " +
                "or enter date in format mm-dd-yyyy");
        List<QuickReply> replies = new LinkedList<>();
        LocalDate date = LocalDate.now();
        int today = date.getDayOfMonth();
        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + ": ";
        replies.add(new QuickReply("Today", today));
        replies.add(new QuickReply("Tomorrow", today + 1));
        for (int i = 2; i < 9; i++) {
            replies.add(new QuickReply(monthName + (today + i), today + i));
        }
        request.setQuickReplies(replies);
        return request;
    }
}
