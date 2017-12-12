package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.client.GoogleMatrixApiClient;
import com.bots.crew.pp.webhook.enteties.db.Cinema;
import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import com.bots.crew.pp.webhook.enteties.payload.CoordinatesPayload;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, GoogleMatrixApiMessage> orderCinemasByDistanceToThePoint(List<Cinema> cinemas, CoordinatesPayload coordinates) {
        Map<Integer, GoogleMatrixApiMessage> messages = new HashMap<>();
        cinemas.sort((Cinema c1, Cinema c2) -> {
            GoogleMatrixApiMessage distance1 = googleMatrixApiClient.getForDistance(buildRequestUrl(getUserOrigin(coordinates), getCinemaOrigin(c1)));
            GoogleMatrixApiMessage distance2 = googleMatrixApiClient.getForDistance(buildRequestUrl(getUserOrigin(coordinates), getCinemaOrigin(c2)));
            messages.put(c1.getId(), distance1);
            messages.put(c2.getId(), distance1);
            return distance1.getRows().get(0).getElements().get(0).getDuration().getValue() - distance2.getRows().get(0).getElements().get(0).getDuration().getValue();
        });
        return messages;
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
