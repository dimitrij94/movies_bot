package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.client.GoogleMatrixApiClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.CinemaGoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GoogleMatrixApiService {
    private Environment environment;
    private String googleMatrixApiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=";
    private GoogleMatrixApiClient googleMatrixApiClient;

    public GoogleMatrixApiService(Environment environment, GoogleMatrixApiClient googleMatrixApiClient) {
        this.environment = environment;
        googleMatrixApiUrl += environment.getProperty("google.maps.distance.matrix.api");
        this.googleMatrixApiClient = googleMatrixApiClient;
    }

    public List<CinemaGoogleMatrixApiMessage> orderCinemasByDistanceToThePoint(List<Cinema> cinemas, CoordinatesPayload coordinates) {
        List<CinemaGoogleMatrixApiMessage> messages = getAllDistances(cinemas, coordinates);
        messages.sort(Comparator.comparingInt(this::getDuration));
        return messages;
    }

    private int getDuration(CinemaGoogleMatrixApiMessage c) {
        return c.getDistanceTo().getRows().get(0).getElements().get(0).getDuration().getValue();
    }

    private List<CinemaGoogleMatrixApiMessage> getAllDistances(List<Cinema> cinemas, CoordinatesPayload coordinates) {
        List<CinemaGoogleMatrixApiMessage> list = new LinkedList<>();
        for (Cinema c : cinemas) {
            GoogleMatrixApiMessage distance = googleMatrixApiClient.getForDistance(buildRequestUrl(getUserOrigin(coordinates), getCinemaOrigin(c)));
            list.add(new CinemaGoogleMatrixApiMessage(c, distance));
        }
        return list;
    }


    private String buildRequestUrl(String originsAddress, String destinationAdress) {
        return String.format(googleMatrixApiUrl, originsAddress, destinationAdress);
    }

    private String getCinemaOrigin(Cinema cinema) {
        return cinema.getLatitude() + "," + cinema.getLongitude();
    }

    private String getUserOrigin(CoordinatesPayload coordinates) {
        return coordinates.getLatitude() + "," + coordinates.getLongitude();
    }


}
