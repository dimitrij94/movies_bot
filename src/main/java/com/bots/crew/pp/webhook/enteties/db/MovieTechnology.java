package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class MovieTechnology {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "technology")
    private List<MovieSession> movieSessions;

    @OneToMany(mappedBy = "technology")
    private List<UserReservation> userReservation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieSession> getMovieSessions() {
        return movieSessions;
    }

    public void setMovieSessions(List<MovieSession> movieSessions) {
        this.movieSessions = movieSessions;
    }

    public List<UserReservation> getUserReservation() {
        return userReservation;
    }

    public void setUserReservation(List<UserReservation> userReservation) {
        this.userReservation = userReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieTechnology that = (MovieTechnology) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
