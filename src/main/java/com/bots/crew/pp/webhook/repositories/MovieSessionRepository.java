package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Integer> {

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAAndSessionTime(int movieId);

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.cinema_id=?2 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAndCinemaAndSessionTime(int movie_id, int cinemaId);

    @Query(value = "SELECT count(s.id) FROM movie_session AS s WHERE s.movie_id=?1 AND s.cinema_id=?2 AND s.technology_id=?3 AND s.session_time>NOW()", nativeQuery = true)
    int countByMovieAndCinemaAndTechnologyAndSessionTime(int movieId, int cinemaId, int technologyId);

    @Query(value = "SELECT max(s.seats_left) FROM movie_session AS s INNER JOIN user_reservation AS r ON r.movie_id=s.movie_id AND r.cinema_id=s.cinema_id AND s.session_time>Now() WHERE r.id=?1", nativeQuery = true)
    int findMaxNumberOfTicketsForUserLastReservation(int reservationId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session as ms inner join user_reservation ur ON ms.cinema_id=ur.cinema_id and ms.movie_id=ur.movie_id and ur.id=?1 where ms.session_time>NOW()", nativeQuery = true)
    List<MovieSession> findAllByMovieAndCinemaLaterToday(Integer userReservationId);

    @Query(value = "SELECT DISTINCT s.* FROM movie_session AS s INNER JOIN movie_technology AS t INNER JOIN user_reservation AS r ON r.id=?1 AND r.movie_id = s.movie_id AND r.cinema_id = s.cinema_id AND s.technology_id = ?2 WHERE s.session_time> NOW() AND s.seats_left >= r.num_of_tickets", nativeQuery = true)
    List<MovieSession> findAllByMovieCinemaTechnologyLaterToday(int reservationId, int technologyId);

    @Query(value = "SELECT DISTINCT ms.* FROM movie_session as ms inner join user_reservation ur ON ms.cinema_id=ur.cinema_id and ms.movie_id=ur.movie_id and ur.id=?1", nativeQuery = true)
    List<MovieSession> findAllByMovieAndCinema(Integer userReservationId);

    @Query(value = "SELECT DISTINCT s.* FROM movie_session AS s INNER JOIN movie_technology AS t INNER JOIN user_reservation AS r ON r.id=?1 AND r.movie_id = s.movie_id AND r.cinema_id = s.cinema_id AND s.technology_id = ?2 WHERE AND s.seats_left >= r.num_of_tickets", nativeQuery = true)
    List<MovieSession> findAllByMovieCinemaTechnology(int reservationId, int technologyId);


    @Query(value = "SELECT DISTINCT ms.session_date FROM movie_session as ms",  nativeQuery = true)
    List<Date> findAvailableSessionDatesForReservation();
}
