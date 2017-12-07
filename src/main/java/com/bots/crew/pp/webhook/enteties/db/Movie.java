package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.List;

@Entity(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "movies")
    @Column(name = "movie_genres")
    private List<MovieGenre> movieGenres;

    @ManyToMany(mappedBy = "movies")
    private List<Cinema> cinemas;

    @OneToMany(mappedBy = "movie")
    private List<MovieSession> movieSessions;

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
}
