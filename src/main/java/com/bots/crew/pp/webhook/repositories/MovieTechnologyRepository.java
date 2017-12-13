package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieTechnologyRepository extends JpaRepository<MovieTechnology, Integer> {
    @Query(value = "SELECT DISTINCT t.*\n" +
            "FROM movie_technology AS t INNER JOIN movie_session AS s\n" +
            "  INNER JOIN user_reservation r ON s.technology_id = t.id \n" +
            "AND s.cinema_id = r.cinema_id AND s.movie_id = r.movie_id AND s.seats_left>=r.num_of_tickets AND s.session_date=r.session_date WHERE r.id=?1", nativeQuery = true)
    List<MovieTechnology> findTechnologiesForUserByMovieAndCinema(int reservationId);

    @Query(value = "SELECT DISTINCT * FROM movie_technology", nativeQuery = true)
    List<MovieTechnology> findAllDistinct();
}
