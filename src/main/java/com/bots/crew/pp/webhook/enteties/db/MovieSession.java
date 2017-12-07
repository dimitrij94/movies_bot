package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.time.LocalTime;

@Entity(name = "movie_session")
public class MovieSession {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "session_time")
    private LocalTime sessionTime;

    private String technology;

    @Column(name = "seats_left")
    private int seatsLeft;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Cinema cinema;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalTime sessionTime) {
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

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }
}
