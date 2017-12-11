package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "SELECT DISTINCT m.* FROM movie AS m INNER JOIN movie_genre_movies AS g INNER JOIN movie_session AS s ON g.movies_id=m.id AND s.movie_id=m.id WHERE s.session_time>NOW() AND g.movie_genres_id=?1", nativeQuery = true)
    List<Movie> findAllByMovieGenresIds(int genreId);

    @Query(value = "SELECT DISTINCT m.* FROM movie m INNER JOIN movie_session ms ON m.id=ms.movie_id " +
            "WHERE ms.session_time>TIME (LOCALTIME())", nativeQuery = true)
    List<Movie> findMoviesTranslatedToday();
}
