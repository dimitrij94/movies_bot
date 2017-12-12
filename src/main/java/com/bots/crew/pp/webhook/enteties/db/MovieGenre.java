package com.bots.crew.pp.webhook.enteties.db;


import javax.persistence.*;
import java.util.List;

@Entity(name = "movie_genre")
public class MovieGenre {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToMany
    private List<Movie> movies;

    @OneToMany(mappedBy = "genre")
    private List<UserReservation> reservations;

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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<UserReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<UserReservation> reservations) {
        this.reservations = reservations;
    }
}
