package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="trailer_url")
    private String trailerUrl;

    @ManyToMany(mappedBy = "movies")
    private List<MovieGenre> movieGenres;

    @ManyToMany(mappedBy = "movies")
    private List<Cinema> cinemas;

    @OneToMany(mappedBy = "movie")
    private List<MovieSession> movieSessions;

    @OneToMany(mappedBy = "movie")
    private List<UserReservation> reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MovieSession> getMovieSessions() {
        return movieSessions;
    }

    public void setMovieSessions(List<MovieSession> movieSessions) {
        this.movieSessions = movieSessions;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public List<UserReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<UserReservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(imageUrl, movie.imageUrl) &&
                Objects.equals(trailerUrl, movie.trailerUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, imageUrl, trailerUrl);
    }
}
