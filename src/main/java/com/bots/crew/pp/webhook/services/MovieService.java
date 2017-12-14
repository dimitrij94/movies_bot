package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findByGenre(int genreId) {
        return movieRepository.findAllByMovieGenresIds(genreId);
    }

    public List<Movie> findTodayMovies() {
        return this.movieRepository.findMoviesTranslatedToday();
    }

    public Movie find(int movieId) {
        return movieRepository.findOne(movieId);
    }
}
