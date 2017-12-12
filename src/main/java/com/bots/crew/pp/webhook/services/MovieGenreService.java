package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.MovieGenre;
import com.bots.crew.pp.webhook.repositories.MovieGenreRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieGenreService {
    private MovieGenreRepository movieGenreRepository;

    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    public MovieGenre find(int id) {
        return movieGenreRepository.findOne(id);
    }

    public MovieGenre find(String replyName) {
        return movieGenreRepository.findOneByName(replyName);
    }
}
