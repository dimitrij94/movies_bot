package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.*;
import com.bots.crew.pp.webhook.repositories.UserReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserReservationService {
    private UserReservationRepository repository;

    public UserReservationService(UserReservationRepository repository) {
        this.repository = repository;
    }

    public void updateForMovie(int movieId, int reservationID) {
        repository.updateSetMovie(movieId, reservationID);
    }

    public UserReservation findUserLatestReservation(String psid) {
        return repository.findLastReservationOfTheUser(psid);
    }

    public UserReservation saveReservationForToday(String psid) {
        UserReservation reservation = new UserReservation();
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        reservation.setSessionDate(date);
        reservation.setTimestamp(new Date());
        reservation.setActivated(false);
        MessengerUser user = new MessengerUser();
        user.setPsid(psid);
        reservation.setUser(user);
        return this.saveReservationForToday(reservation);
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

    public UserReservation saveReservationForToday(UserReservation reservation) {
        return repository.save(reservation);
    }

    public List<UserReservation> findLast10ActiveReservations(String psid) {
        return repository.findTop10OrOrderBySessionTime(psid);
    }

    public void deleteNotActiveReservations(String psid) {
        this.repository.deleteAllNotActivated(psid);
    }

    @Transactional
    public UserReservation createAndSetMovie(String psid, int movieId) {
        UserReservation userReservation = new UserReservation();
        userReservation.setActivated(false);
        userReservation.setTimestamp(new Date());

        Movie m = new Movie();
        m.setId(movieId);
        userReservation.setMovie(m);

        MessengerUser user = new MessengerUser();
        user.setPsid(psid);
        userReservation.setUser(user);

        return repository.save(userReservation);
    }
}
