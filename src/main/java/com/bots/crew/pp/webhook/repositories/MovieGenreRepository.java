package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
    @Query(value = "select DISTINCT mg.* from movie_genre as mg inner join movie_genre_movies as mgm ON mg.id = mgm.movie_genres_id", nativeQuery = true)
    List<MovieGenre> finAllWithKnownMovies();

}
