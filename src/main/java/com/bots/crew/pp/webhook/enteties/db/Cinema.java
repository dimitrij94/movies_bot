package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.List;
@Entity(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany
    private List<Movie> movies;

    @OneToMany(mappedBy = "cinema")
    private List<MovieSession> sessions;

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

    public List<MovieSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<MovieSession> sessions) {
        this.sessions = sessions;
    }
}
