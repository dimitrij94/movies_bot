package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.CinemaGoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import com.bots.crew.pp.webhook.repositories.CinemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CinemaService {
    private CinemaRepository repository;
    private GoogleMatrixApiService googleMatrixApiService;

    public CinemaService(CinemaRepository repository, GoogleMatrixApiService googleMatrixApiService) {
        this.repository = repository;
        this.googleMatrixApiService = googleMatrixApiService;
    }

    public Cinema save(Cinema cinema) {
        return this.repository.save(cinema);
    }

    public List<Cinema> findCinemasForMovieToday(int movieId) {
        return repository.findCinemaForMovieSessionToday(movieId);
    }

    public List<Cinema> findCinemasForMovieSessionAtDate(int movieId, Date date) {
        return repository.findCinemaForMovieSessionAtDate(movieId, date);
    }

    public Cinema find(int cinemaId) {
        return repository.findOne(cinemaId);
    }

    public List<Cinema> findAll() {
        return repository.findAll();
    }

    public List<CinemaGoogleMatrixApiMessage> findCinemasOrderByDistanceTo(List<Cinema> cinemas, double longitude, double latitude) {
        return googleMatrixApiService.orderCinemasByDistanceToThePoint(cinemas, new CoordinatesPayload(longitude, latitude));
    }
}
