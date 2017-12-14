package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.PpApplication;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CinemaServiceTest {
    @Autowired
    CinemaService cinemaService;
    @Autowired
    MovieSessionService sessionService;
    @Autowired
    MovieService movieService;

    public CinemaServiceTest() {
    }

    @After
    public void tearDown() throws Exception {
        sessionService.deleteAllSessions();
    }

    @Test
    public void findCinemasForMovieToday() {
        Cinema cinema = cinemaService.find(1);
        Movie cinemaMovie = movieService.find(1);

        MovieSession session = new MovieSession();
        Date today = UtilsService.convertToDate(LocalDate.now());
        session.setSessionDate(today);
        session.setCinema(cinema);
        session.setMovie(cinemaMovie);
        session = sessionService.save(session);
        List<Cinema> cinemasForMovieToday = cinemaService.findCinemasForMovieToday(cinemaMovie.getId());
        assertFalse(cinemasForMovieToday.isEmpty());
        assertTrue(cinemasForMovieToday.size() == 1);
        assertTrue(cinemasForMovieToday.get(0).equals(cinema));

        session.setSessionDate(UtilsService.addDays(today, 1));
        sessionService.save(session);
        List<Cinema> emptyListCinemas = cinemaService.findCinemasForMovieToday(cinemaMovie.getId());
        assertTrue(emptyListCinemas.isEmpty());

        Movie notACinemaMovie = movieService.find(4);
        session.setSessionDate(today);
        session.setMovie(notACinemaMovie);
        session = sessionService.save(session);

        emptyListCinemas = cinemaService.findCinemasForMovieToday(cinemaMovie.getId());
        assertTrue(emptyListCinemas.isEmpty());
    }

    @Test
    public void findCinemasForMovieSessionAtDate() {
        Cinema cinema = cinemaService.find(1);
        Movie cinemaMovie = movieService.find(1);

        MovieSession session = new MovieSession();
        Date tomorrow = UtilsService.convertToDate(LocalDate.now());
        session.setSessionDate(tomorrow);
        session.setCinema(cinema);
        session.setMovie(cinemaMovie);
        session = sessionService.save(session);
        List<Cinema> cinemasForMovieToday = cinemaService.findCinemasForMovieSessionAtDate(cinemaMovie.getId(), tomorrow);
        assertFalse(cinemasForMovieToday.isEmpty());
        assertTrue(cinemasForMovieToday.size() == 1);
        assertTrue(cinemasForMovieToday.get(0).equals(cinema));


        session.setSessionDate(UtilsService.addDays(tomorrow, 1));
        sessionService.save(session);
        List<Cinema> emptyListCinemas = cinemaService.findCinemasForMovieSessionAtDate(cinemaMovie.getId(), tomorrow);
        assertTrue(emptyListCinemas.isEmpty());

        Movie notACinemaMovie = movieService.find(4);
        session.setSessionDate(tomorrow);
        session.setMovie(notACinemaMovie);
        session = sessionService.save(session);

        emptyListCinemas = cinemaService.findCinemasForMovieToday(cinemaMovie.getId());
        assertTrue(emptyListCinemas.isEmpty());
    }

    public CinemaService getCinemaService() {
        return cinemaService;
    }

    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    public MovieSessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(MovieSessionService sessionService) {
        this.sessionService = sessionService;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}