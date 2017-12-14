package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.repositories.MovieSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class MovieSessionService {
    private MovieSessionRepository repository;

    public MovieSessionService(MovieSessionRepository repository) {
        this.repository = repository;
    }

    public List<MovieSession> findSessionsForMovieCinemaAndTechnology(int reservationId, int technologyId) {
        return repository.findAllByMovieCinemaTechnologyLaterToday(reservationId, technologyId);
    }

    public int findMaxNumberOfTicketsForUserLastReservation(int userReservationId) {
        return this.repository.findMaxNumberOfTicketsForUserLastReservation(userReservationId);
    }

    public List<MovieSession> findSessionForMovieAndCinema(Integer id) {
        return this.repository.findAllByMovieAndCinemaLaterToday(id);
    }

    public MovieSession save(MovieSession movieSession) {
        return repository.save(movieSession);
    }

    public List<MovieSession> findMoviesSessionsWithTechnology(UserReservation reservation, MovieTechnology selectedTechnology) {
        List<MovieSession> sessions;
        if (isReservationForToday(reservation)) {
            sessions = repository.findAllByMovieCinemaTechnologyLaterToday(reservation.getId(), selectedTechnology.getId());
        } else {
            sessions = this.repository.findAllByMovieCinemaTechnology(reservation.getId(), selectedTechnology.getId());
        }
        return sessions;
    }

    public List<MovieSession> findMoviesSessionsWithoutTechnology(UserReservation reservation) {
        List<MovieSession> sessions;
        if (isReservationForToday(reservation)) {
            sessions = repository.findAllByMovieAndCinemaLaterToday(reservation.getId());
        } else {
            sessions = this.repository.findAllByMovieAndCinema(reservation.getId());
        }
        return sessions;
    }

    private boolean isReservationForToday(UserReservation reservation) {
        return UtilsService.convertToLocalDate(reservation.getSessionDate()).getDayOfMonth() == LocalDate.now().getDayOfMonth();
    }

    public int countSessionsBySessionDate(LocalDate userDate, Movie movie) {
        return repository.countBySessionDateAndMovie(UtilsService.convertToDate(userDate), movie);
    }

    public int countSessionsToday(Date userDate) {
        return repository.countBySessionDateAndSessionTime(userDate);
    }

    public void deleteAllSessions() {
        repository.deleteAll();
    }
/*
    public List<Date> findAvailableSessionDatesForReservation() {
        return repository.findAvailableSessionDatesForReservation();
    }
    */
}
