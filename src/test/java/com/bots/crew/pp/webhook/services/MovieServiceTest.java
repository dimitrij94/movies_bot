package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieSessionService movieSessionService;

    public MovieServiceTest() {
    }

    @Test
    public void findByGenre() {
        List<Movie> actionMovies = movieService.findByGenre(2);
        Movie justiceLeague = movieService.find(2);
        Movie valerian = movieService.find(3);
        assertTrue(actionMovies.contains(justiceLeague));
        assertTrue(actionMovies.contains(valerian));
        assertTrue(actionMovies.size() == 2);
    }

    @Test
    public void findTodayMovies() {
        Movie justiceLeague = movieService.find(2);
        Movie valerian = movieService.find(3);
        Movie foreigner = movieService.find(4);

        MovieSession todaySession = new MovieSession();

        Date validTime = UtilsService.convertToDate(LocalTime.now().plusHours(1), LocalDate.now());
        Date invalidTime = UtilsService.convertToDate(LocalTime.now().minusHours(1), LocalDate.now());

        todaySession.setMovie(justiceLeague);
        todaySession.setSessionDate(UtilsService.convertToDate(LocalDate.now()));
        todaySession.setSessionTime(validTime);
        movieSessionService.save(todaySession);


        MovieSession todayWithInvalidTime = todaySession;
        todayWithInvalidTime.setId(null);
        todayWithInvalidTime.setSessionTime(invalidTime);
        todayWithInvalidTime.setMovie(foreigner);
        movieSessionService.save(todaySession);

        MovieSession tommorowSession = todaySession;
        tommorowSession.setId(null);
        tommorowSession.setSessionDate(UtilsService.convertToDate(LocalDate.now().plusDays(1)));
        todaySession.setMovie(valerian);
        movieSessionService.save(tommorowSession);


        List foundMovies = movieService.findTodayMovies();
        assertTrue(foundMovies.contains(justiceLeague));
        assertFalse(foundMovies.contains(valerian));
        assertFalse(foundMovies.contains(foreigner));
        assertTrue(foundMovies.size() == 1);
    }
}