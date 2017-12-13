package com.bots.crew.pp.webhook.initializers;

import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.db.Movie;
import com.bots.crew.pp.webhook.enteties.db.MovieSession;
import com.bots.crew.pp.webhook.enteties.db.MovieTechnology;
import com.bots.crew.pp.webhook.repositories.MovieSessionRepository;
import com.bots.crew.pp.webhook.services.UtilsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class MovieSessionInitializer implements InitializingBean {

    private MovieSessionRepository movieSessionRepository;
    private Environment environment;

    public MovieSessionInitializer(MovieSessionRepository movieSessionRepository, Environment environment) {
        this.movieSessionRepository = movieSessionRepository;
        this.environment = environment;
    }

    public void initialize() {
        MovieSession session = new MovieSession();
        Cinema cinema = new Cinema();
        Movie movie = new Movie();
        MovieTechnology movieTechnology = new MovieTechnology();
        int[] seatsLeft = {1, 10, 20, 30};
        LocalDate sessionDate = LocalDate.now();
        int[] hours = {11, 12, 15, 19, 23};
        int[] minutes = {00, 15, 30, 00, 00};

        for (int i = 1; i <= 2; i++) {
            cinema.setId(i);
            session.setCinema(cinema);
            for (int tec = 1; tec <= 3; tec++) {
                movieTechnology.setId(tec);
                session.setTechnology(movieTechnology);
                for (int j = 1; j <= 4; j++) {
                    movie.setId(j);
                    session.setMovie(movie);
                    session.setSeatsLeft(seatsLeft[j - 1]);
                    for (int d = 0; d < 4; d++) {
                        LocalDate date = LocalDate.of(sessionDate.getYear(), sessionDate.getMonth(), sessionDate.getDayOfMonth() + d);

                        session.setSessionDate(UtilsService.convertToDate(date));

                        for (int t = 0; t < 4; t++) {
                            LocalTime time = LocalTime.of(hours[t], minutes[t]);
                            Date sessionTime = Date.from(time.atDate(LocalDate.of(sessionDate.getYear(), sessionDate.getMonth(), sessionDate.getDayOfMonth())).
                                    atZone(ZoneId.systemDefault()).toInstant());

                            session.setSessionTime(sessionTime);
                            movieSessionRepository.save(session);
                            session.setId(null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.environment.getProperty("spring.jpa.hibernate.ddl-auto").equals("create-drop")) {
            initialize();
        }
    }
}
