package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.CinemaGoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import com.bots.crew.pp.webhook.repositories.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CinemaService {
    private CinemaRepository repository;
    private GoogleMatrixApiService googleMatrixApiService;

    public CinemaService(CinemaRepository repository, GoogleMatrixApiService googleMatrixApiService) {
        this.repository = repository;
        this.googleMatrixApiService = googleMatrixApiService;
    }

    public List<Cinema> findCinemasForMovie(int movieId) {
        return repository.findCinemaForMovie(movieId);
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
