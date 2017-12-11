package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.client.GoogleMatrixApiClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiRequest;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleMatrixApiService {
    private Environment environment;
    private String googleMatrixApiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=%s&destinations=%s&key=";
    private GoogleMatrixApiClient googleMatrixApiClient;

    public GoogleMatrixApiService(Environment environment, GoogleMatrixApiClient googleMatrixApiClient) {
        this.environment = environment;
        googleMatrixApiUrl += environment.getProperty("google.maps.distance.matrix.api");
        this.googleMatrixApiClient = googleMatrixApiClient;
    }

    public List<Cinema> orderCinemasByDistanceToThePoint(List<Cinema> cinemas, CoordinatesPayload coordinates) {
        cinemas.sort((Cinema c1, Cinema c2) -> {
            GoogleMatrixApiRequest distance1 = googleMatrixApiClient.getForDistance(buildRequestUrl(getUserOrigin(coordinates), getCinemaOrigin(c1)));
            GoogleMatrixApiRequest distance2 = googleMatrixApiClient.getForDistance(buildRequestUrl(getUserOrigin(coordinates), getCinemaOrigin(c2)));
            return distance1.getRows().get(0).getElements().get(0).getDuration().getValue() - distance2.getRows().get(0).getElements().get(0).getDuration().getValue();
        });
        return cinemas;
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
