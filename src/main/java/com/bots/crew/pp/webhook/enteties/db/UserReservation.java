package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user_reservation")
public class UserReservation {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "num_of_tickets", nullable = true)
    private Integer numberOfTickets;

    @Column(name = "is_activated")
    private boolean isActivated;
    @ManyToOne(optional = true)
    private Cinema cinema;
    @ManyToOne(optional = true)
    private Movie movie;
    @ManyToOne(optional = true)
    private MovieSession session;
    @ManyToOne(optional = true)
    private MessengerUser user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieSession getSession() {
        return session;
    }

    public void setSession(MovieSession session) {
        this.session = session;
    }

    public MessengerUser getUser() {
        return user;
    }

    public void setUser(MessengerUser user) {
        this.user = user;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReservation that = (UserReservation) o;

        if (numberOfTickets != that.numberOfTickets) return false;
        if (isActivated != that.isActivated) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cinema != null ? !cinema.equals(that.cinema) : that.cinema != null) return false;
        if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
        if (session != null ? !session.equals(that.session) : that.session != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + numberOfTickets;
        result = 31 * result + (isActivated ? 1 : 0);
        result = 31 * result + (cinema != null ? cinema.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
