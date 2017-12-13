package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Integer> {

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAAndSessionTime(int movieId);

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.cinema_id=?2 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAndCinemaAndSessionTime(int movie_id, int cinemaId);

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.cinema_id=?2 AND s.technology_id=?3 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAndCinemaAndTechnologyAndSessionTime(int movieId, int cinemaId, int technologyId);

    @Query(value = "SELECT max(s.seats_left) FROM movie_session AS s INNER JOIN user_reservation AS r ON r.movie_id=s.movie_id AND r.cinema_id=s.cinema_id AND s.session_time>Now() AND s.session_date=r.session_date WHERE r.id=?1", nativeQuery = true)
    int findMaxNumberOfTicketsForUserLastReservation(int reservationId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session AS ms INNER JOIN user_reservation ur ON ms.cinema_id=ur.cinema_id AND ms.movie_id=ur.movie_id AND ms.session_date=ur.session_date AND ur.id=?1 WHERE ms.session_time>NOW()", nativeQuery = true)
    List<MovieSession> findAllByMovieAndCinemaLaterToday(Integer userReservationId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session AS ms INNER JOIN movie_technology AS t INNER JOIN user_reservation AS r ON r.id=?1 AND r.movie_id = ms.movie_id AND r.cinema_id = ms.cinema_id AND ms.technology_id = ?2 AND ms.session_date=r.session_date WHERE ms.session_time> NOW() AND ms.seats_left >= r.num_of_tickets", nativeQuery = true)
    List<MovieSession> findAllByMovieCinemaTechnologyLaterToday(int reservationId, int technologyId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session AS ms INNER JOIN user_reservation ur ON ms.cinema_id=ur.cinema_id AND ms.movie_id=ur.movie_id AND ms.session_date=ur.session_date AND ur.id=?1", nativeQuery = true)
    List<MovieSession> findAllByMovieAndCinema(Integer userReservationId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session AS ms INNER JOIN movie_technology AS t INNER JOIN user_reservation AS r ON r.id=?1 AND r.movie_id = ms.movie_id AND r.cinema_id = ms.cinema_id AND ms.session_date=r.session_date AND ms.technology_id = ?2 WHERE  ms.seats_left >= r.num_of_tickets", nativeQuery = true)
    List<MovieSession> findAllByMovieCinemaTechnology(int reservationId, int technologyId);

    int countBySessionDateAndMovie(Date userDate, Movie movie);

    @Query(value = "SELECT count(ms.id) FROM movie_session ms WHERE ms.session_date=?1 AND ms.session_time>TIME(NOW())", nativeQuery = true)
    int countBySessionDateAndSessionTime(Date userDate);
}
