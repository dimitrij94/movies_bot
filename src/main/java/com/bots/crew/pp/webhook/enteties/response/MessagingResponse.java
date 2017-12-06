package com.bots.crew.pp.webhook.enteties.response;

import com.bots.crew.pp.webhook.enteties.Sender;

public class MessagingResponse {
    private MessagingResponseContent responseContent;
    private Sender recipiant;

    public MessagingResponseContent getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(MessagingResponseContent responseContent) {
        this.responseContent = responseContent;
    }

    public Sender getRecipiant() {
        return recipiant;
    }

    public void setRecipiant(Sender recipiant) {
        this.recipiant = recipiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagingResponse response = (MessagingResponse) o;

        if (responseContent != null ? !responseContent.equals(response.responseContent) : response.responseContent != null)
            return false;
        return recipiant != null ? recipiant.equals(response.recipiant) : response.recipiant == null;
    }

    @Override
    public int hashCode() {
        int result = responseContent != null ? responseContent.hashCode() : 0;
        result = 31 * result + (recipiant != null ? recipiant.hashCode() : 0);
        return result;
    }
}
