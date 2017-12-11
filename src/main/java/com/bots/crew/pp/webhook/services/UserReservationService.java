package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import com.bots.crew.pp.webhook.repositories.UserReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserReservationService {
    private UserReservationRepository repository;

    public UserReservationService(UserReservationRepository repository) {
        this.repository = repository;
    }

    public void saveForMovie(int movieId, String psid) {
        repository.createSetMovie(movieId, psid);
    }

    public UserReservation findUserLatestReservation(String psid) {
        return repository.findLastReservationOfTheUser(psid);
    }

    /*
        public void saveCinema(int cinemaId, String psid) {
            UserReservation userReservation = repository.findLastReservationOfTheUser(psid);
            repository.updateSetCinema(userReservation.getId(), cinemaId);
        }
    */
    @Transactional
    public void saveCinema(int cinemaId, UserReservation userReservation) {
        Cinema cinema = new Cinema();
        cinema.setId(cinemaId);
        userReservation.setCinema(cinema);
        repository.save(userReservation);
    }

    public UserReservation saveNumberOfTickets(String psid, int numOfTickets) {
        UserReservation userReservation = repository.findLastReservationOfTheUser(psid);
        userReservation.setNumberOfTickets(numOfTickets);
        return repository.save(userReservation);
    }

    @Transactional
    public UserReservation updateForMovieSession(UserReservation reservation, int sessionId) {
        MovieSession session = new MovieSession();
        session.setId(sessionId);
        reservation.setSession(session);
        return repository.save(reservation);
    }

    public UserReservation save(UserReservation reservation) {
        return repository.save(reservation);
    }
}
