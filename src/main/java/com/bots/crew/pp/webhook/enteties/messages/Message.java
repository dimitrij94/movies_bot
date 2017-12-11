package com.bots.crew.pp.webhook.enteties.messages;

import com.bots.crew.pp.webhook.enteties.request.QuickReply;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Message {

    private String mid;
    private int sqt;
    private String text;
    @JsonProperty("quick_reply")
    private QuickReply quickReply;
    private List<MessageAttachment> attachments;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getSqt() {
        return sqt;
    }

    public void setSqt(int sqt) {
        this.sqt = sqt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MessageAttachment> attachments) {
        this.attachments = attachments;
    }

    public QuickReply getQuickReply() {
        return quickReply;
    }

    public void setQuickReply(QuickReply quickReply) {
        this.quickReply = quickReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (sqt != message.sqt) return false;
        if (mid != null ? !mid.equals(message.mid) : message.mid != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (quickReply != null ? !quickReply.equals(message.quickReply) : message.quickReply != null)
            return false;
        return attachments != null ? attachments.equals(message.attachments) : message.attachments == null;
    }

    @Override
    public int hashCode() {
        int result = mid != null ? mid.hashCode() : 0;
        result = 31 * result + sqt;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (quickReply != null ? quickReply.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }
}
