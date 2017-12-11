package com.bots.crew.pp.webhook.enteties.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordinatesPayload {
    @JsonProperty("long")
    private double longitude;

    @JsonProperty("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
