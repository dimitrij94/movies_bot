package com.bots.crew.pp.webhook.builders.quick;

import com.bots.crew.pp.webhook.builders.QuickReplyBuilder;
import com.bots.crew.pp.webhook.enteties.recipient.Recipient;
import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.bots.crew.pp.webhook.enteties.request.QuickReplyRequestContent;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class DayOfTheSessionBuilder extends QuickReplyBuilder {
    private String psid;

    public DayOfTheSessionBuilder(String psid) {
        this.psid = psid;
    }

    @Override
    protected Recipient getRecipient() {
        return super.getQuickRecipient(psid);
    }

    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        List<QuickReply> replies = new LinkedList<>();
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        String month = now.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String title;
        for (int i = 0; i < 5; i++) {
            int dayOfMonth = today + i;
            if (dayOfMonth == today) {
                title = "Today";
            } else if (dayOfMonth == today + 1) {
                title = "Tomorrow";
            } else {
                title = month + " " + Integer.toString(dayOfMonth);
            }
            replies.add(new QuickReply(title, dayOfMonth));
        }
        requestContent.setText("Send me the date when do you want to watch this movie. Click the button bellow or type the date in \"mm-dd-yyyy\" format.");
        requestContent.setQuickReplies(replies);
        return requestContent;
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
/*
    @Override
    protected QuickReplyRequestContent getRequestContent() {
        QuickReplyRequestContent requestContent = new QuickReplyRequestContent();
        List<QuickReply> replies = new LinkedList<>();
        int today = LocalDate.now().getDayOfMonth();
        for (Date date : dates) {
            LocalDate d = convertToLocalDate(date);
            int dayOfMonth = d.getDayOfMonth();
            String title;
            if (dayOfMonth == today) {
                title = "Today";
            } else if (dayOfMonth == today + 1) {
                title = "Tomorrow";
            } else {
                title = Integer.toString(dayOfMonth);
            }
            replies.add(new QuickReply(title, dayOfMonth));
        }

        requestContent.setQuickReplies(replies);
        return requestContent;
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
    */
}
