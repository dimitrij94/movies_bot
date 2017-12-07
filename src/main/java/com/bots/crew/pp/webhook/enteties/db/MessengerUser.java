package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "messenger_user")
public class MessengerUser {
    @Id
    @GeneratedValue
    Integer id;

    String psid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessengerUser that = (MessengerUser) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return psid != null ? psid.equals(that.psid) : that.psid == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (psid != null ? psid.hashCode() : 0);
        return result;
    }
}
