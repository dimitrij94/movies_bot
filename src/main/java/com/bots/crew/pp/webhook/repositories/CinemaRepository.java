package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    @Query(value = "SELECT c.* from cinema as c INNER  join cinema_movies cm on c.id = cm.cinemas_id " +
            "where cm.movies_id=?1", nativeQuery = true)
    List<Cinema> findCinemaForMovie(int movieId);


    /**
     * @return this is bullshit
     */
    @Query(value = "SELECT c.* from cinema as c ORDER BY POWER((c.longitude-?1),2)+POWER(c.latitude-?2, 2) DESC LIMIT 10", nativeQuery = true)
    List<Cinema> findCinemasClosestTo(double longitude, double latitude);
}
