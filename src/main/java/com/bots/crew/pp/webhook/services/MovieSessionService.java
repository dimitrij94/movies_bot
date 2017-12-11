package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.repositories.MovieSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieSessionService {
    private MovieSessionRepository repository;

    public MovieSessionService(MovieSessionRepository repository) {
        this.repository = repository;
    }

    public List<MovieSession> findSessionsForMovieCinemaAndTechnology(int reservationId, int technologyId) {
        return repository.findAllByMovieCinemaTechnology(reservationId, technologyId);
    }

    public int findMaxNumberOfTicketsForUserLastReservation(int userReservationId) {
        return this.repository.findMaxNumberOfTicketsForUserLastReservation(userReservationId);
    }
}
