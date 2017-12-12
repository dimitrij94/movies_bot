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

    public UserReservation updateForMovie(int movieId, UserReservation userReservation) {
        Movie movie = new Movie();
        movie.setId(movieId);
        userReservation.setMovie(movie);
        return repository.save(userReservation);
    }

    public UserReservation findUserLatestReservation(String psid) {
        return repository.findLastReservationOfTheUser(psid);
    }

    public UserReservation saveReservationForToday(UserReservation reservation, MessengerUser user) {
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        reservation.setTimestamp(new Date());
        reservation.setActivated(false);
        reservation.setUser(user);
        return this.saveReservationForDate(reservation, date);
    }

    /*
        public void saveCinema(int cinemaId, String psid) {
            UserReservation userReservation = repository.findLastReservationOfTheUser(psid);
            repository.updateSetCinema(userReservation.getId(), cinemaId);
        }
    */
    @Transactional
    public UserReservation saveCinema(int cinemaId, UserReservation userReservation) {
        Cinema cinema = new Cinema();
        cinema.setId(cinemaId);
        userReservation.setCinema(cinema);
        return repository.save(userReservation);
    }

    public UserReservation saveNumberOfTickets(UserReservation userReservation, int numOfTickets) {
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

    public UserReservation saveReservationForDate(UserReservation reservation, Date userDate) {
        reservation.setSessionDate(userDate);
        return repository.save(reservation);
    }

    public List<UserReservation> findLast10ActiveReservations(String psid) {
        return repository.findTop10OrOrderBySessionTime(psid);
    }

    public void deleteNotActiveReservations(String psid) {
        this.repository.deleteAllNotActivated(psid);
    }


    @Transactional
    public UserReservation updateSetMovie(UserReservation userReservation, int movieId) {
        Movie movie = new Movie();
        movie.setId(movieId);
        userReservation.setMovie(movie);
        return repository.save(userReservation);
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

    public UserReservation createEmptyReservation(MessengerUser user) {
        UserReservation reservation = new UserReservation();
        reservation.setTimestamp(new Date());
        reservation.setUser(user);
        reservation.setActivated(false);
        return repository.save(reservation);
    }

    public UserReservation activateReservation(UserReservation reservation) {
        reservation.setActivated(true);
        return repository.save(reservation);
    }

    public UserReservation save(UserReservation reservation) {
        return repository.save(reservation);
    }

    public UserReservation saveEmptyReservation(MessengerUser user) {
        UserReservation reservation = new UserReservation();
        reservation.setActivated(false);
        reservation.setUser(user);
        reservation.setTimestamp(new Date());
        return repository.save(reservation);
    }
}
