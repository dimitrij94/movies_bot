package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.repositories.MovieTechnologyRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieTechnologyService implements InitializingBean {
    private MovieTechnologyRepository repository;
    private static final Map<Integer, MovieTechnology> movieTechnologies = new HashMap<>();

    public MovieTechnologyService(MovieTechnologyRepository repository) {
        this.repository = repository;
    }

    public List<MovieTechnology> getAvailableTechnologies(int reservationId) {
        return repository.findTechnologiesForUserByMovieAndCinema(reservationId);
    }

    public MovieTechnology find(int technologyId) {
        return movieTechnologies.get(technologyId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<MovieTechnology> movieTechnologies = this.repository.findAllDistinct();
        for (MovieTechnology tech : movieTechnologies) {
            MovieTechnologyService.movieTechnologies.put(tech.getId(), tech);
        }
    }
}
