package com.bots.crew.pp.webhook.enteties.recipient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipient {
    private String id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("user_ref")
    private String userRef;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipient recipient = (Recipient) o;

        if (id != null ? !id.equals(recipient.id) : recipient.id != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(recipient.phoneNumber) : recipient.phoneNumber != null)
            return false;
        if (userRef != null ? !userRef.equals(recipient.userRef) : recipient.userRef != null) return false;
        return name != null ? name.equals(recipient.name) : recipient.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (userRef != null ? userRef.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
