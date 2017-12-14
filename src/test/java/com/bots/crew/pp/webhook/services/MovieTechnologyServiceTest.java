package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.enteties.db.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieTechnologyServiceTest {
    @Autowired
    private UserReservationService userReservationService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private MovieTechnologyService movieTechnologyService;
    @Autowired
    private MovieSessionService movieSessionService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getAvailableTechnologies() {
        UserReservation userReservation = new UserReservation();
        Date validDate = UtilsService.convertToDate(LocalDate.now());
        Date invalidDate = UtilsService.convertToDate(LocalDate.now().plusDays(1));
        Cinema validCinema = cinemaService.find(1);
        Cinema invalidCinema = cinemaService.find(2);
        Movie validMovie = movieService.find(1);
        Movie invalidMovie = movieService.find(2);
        int validNumTickets = 1;

        userReservation.setSessionDate(validDate);
        userReservation.setCinema(validCinema);
        userReservation.setMovie(validMovie);
        userReservation.setNumberOfTickets(validNumTickets);
        userReservation = userReservationService.save(userReservation);

        MovieTechnology validTechnology = movieTechnologyService.find(1);

        MovieSession validSession = new MovieSession();
        validSession.setMovie(validMovie);
        validSession.setSessionDate(validDate);
        validSession.setCinema(validCinema);
        validSession.setTechnology(validTechnology);
        validSession.setSeatsLeft(1);
        validSession = movieSessionService.save(validSession);
        int validSessionId = validSession.getId();


        MovieSession invalidMovieSession = validSession;
        invalidMovieSession.setMovie(invalidMovie);
        invalidMovieSession.setId(null);
        invalidMovieSession = movieSessionService.save(invalidMovieSession);

        validSession.setMovie(validMovie);

        MovieSession invalidCinemaSession = validSession;
        invalidCinemaSession.setCinema(invalidCinema);
        invalidCinemaSession.setId(null);
        invalidCinemaSession = movieSessionService.save(invalidCinemaSession);

        validSession.setCinema(validCinema);

        MovieSession invalidDateSession = validSession;
        invalidDateSession.setSessionDate(invalidDate);
        invalidDateSession.setId(null);
        invalidDateSession = movieSessionService.save(invalidDateSession);

        validSession.setSessionDate(validDate);

        MovieSession invalidNumOfTicketsSession = validSession;
        invalidNumOfTicketsSession.setSeatsLeft(0);
        invalidNumOfTicketsSession.setId(null);
        movieSessionService.save(invalidNumOfTicketsSession);
        validSession.setId(validSessionId);
        validSession.setSeatsLeft(validNumTickets);

        List<MovieTechnology> availableTech = movieTechnologyService.getAvailableTechnologies(userReservation.getId());

        assertTrue(availableTech.contains(validTechnology));
        assertTrue(availableTech.size()==1);

    }

    private void saveValidReservation() {

    }
}