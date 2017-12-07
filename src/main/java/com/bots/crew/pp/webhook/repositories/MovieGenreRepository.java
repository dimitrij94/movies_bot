package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
}
