package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity(name = "movie_session")
public class MovieSession {
    @Id
    @GeneratedValue
    private Integer id;

    @Temporal(TemporalType.TIME)
    @Column(name = "session_time")
    private Date sessionTime;


    @ManyToOne
    private MovieTechnology technology;

    @Column(name = "seats_left")
    private int seatsLeft;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Cinema cinema;

    @OneToMany(mappedBy = "session")
    private List<UserReservation> reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public MovieTechnology getTechnology() {
        return technology;
    }

    public void setTechnology(MovieTechnology technology) {
        this.technology = technology;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public List<UserReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<UserReservation> reservations) {
        this.reservations = reservations;
    }

}
