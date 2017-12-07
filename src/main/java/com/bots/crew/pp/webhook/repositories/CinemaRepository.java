package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
}
