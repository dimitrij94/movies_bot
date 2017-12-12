package com.bots.crew.pp.webhook.enteties.db;

import com.bots.crew.pp.webhook.MessangerUserStatus;

import javax.persistence.*;
import java.util.List;

@Entity(name = "messenger_user")
public class MessengerUser {

    @Id
    private String psid;

    @Enumerated
    private MessangerUserStatus status;

    @Enumerated
    private MessangerUserStatus previousStatus;

    @OneToMany(mappedBy = "user")
    private List<UserReservation> reservations;

    public MessengerUser() {
    }

    public MessengerUser(String psid, MessangerUserStatus status) {
        this.status = status;
        this.psid = psid;
    }

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    public MessangerUserStatus getStatus() {
        return status;
    }

    public void setStatus(MessangerUserStatus status) {
        this.status = status;
    }

    public List<UserReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<UserReservation> reservations) {
        this.reservations = reservations;
    }

    public MessangerUserStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(MessangerUserStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessengerUser that = (MessengerUser) o;

        if (psid != null ? !psid.equals(that.psid) : that.psid != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = psid != null ? psid.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
