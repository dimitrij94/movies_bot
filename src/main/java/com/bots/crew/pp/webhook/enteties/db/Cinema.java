package com.bots.crew.pp.webhook.enteties.db;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "image_url")
    private String imageUrl;
    private String name;
    private String address;
    @ManyToMany
    private List<Movie> movies;

    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "cinema")
    private List<UserReservation>reservations;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

        Cinema cinema = (Cinema) o;

        if (id != null ? !id.equals(cinema.id) : cinema.id != null) return false;
        if (imageUrl != null ? !imageUrl.equals(cinema.imageUrl) : cinema.imageUrl != null) return false;
        if (name != null ? !name.equals(cinema.name) : cinema.name != null) return false;
        if (movies != null ? !movies.equals(cinema.movies) : cinema.movies != null) return false;
        return sessions != null ? sessions.equals(cinema.sessions) : cinema.sessions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (movies != null ? movies.hashCode() : 0);
        result = 31 * result + (sessions != null ? sessions.hashCode() : 0);
        return result;
    }
}
