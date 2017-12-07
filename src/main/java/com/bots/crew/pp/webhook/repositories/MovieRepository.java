package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
