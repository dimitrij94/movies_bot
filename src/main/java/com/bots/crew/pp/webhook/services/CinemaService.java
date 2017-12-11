package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.repositories.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    private CinemaRepository repository;

    public CinemaService(CinemaRepository repository) {
        this.repository = repository;
    }

    public List<Cinema> findCinemasForMovie(int movieId) {
        return repository.findCinemaForMovie(movieId);
    }

    public Cinema find(int cinemaId) {
        return repository.findOne(cinemaId);
    }

    public List<Cinema> findCinemasOrderByDistanceTo(double longitude, double latitude) {
        return repository.findCinemasClosestTo(longitude, latitude);
    }
}
