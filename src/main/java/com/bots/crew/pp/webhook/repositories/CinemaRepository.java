package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    @Query(value = "SELECT DISTINCT c.* FROM cinema AS c INNER  JOIN movie_session AS ms ON ms.cinema_id=c.id WHERE ms.movie_id=?1 AND ms.session_date=DATE(NOW())", nativeQuery = true)
    List<Cinema> findCinemaForMovieSessionToday(int movieId);

    @Query(value = "SELECT DISTINCT c.* FROM cinema AS c INNER JOIN movie_session AS ms ON ms.cinema_id=c.id WHERE ms.movie_id=?1 AND ms.session_date=?2", nativeQuery = true)
    List<Cinema> findCinemaForMovieSessionAtDate(int movieId, Date date);


    /**
     * @return this is bullshit
     */
    @Query(value = "SELECT c.* FROM cinema AS c ORDER BY POWER((c.longitude-?1),2)+POWER(c.latitude-?2, 2) DESC LIMIT 10", nativeQuery = true)
    List<Cinema> findCinemasClosestTo(double longitude, double latitude);
}
