package com.bots.crew.pp.webhook.enteties.messages.matrix_api;

public class GoogleMatrixApiRequestElements {
    private GoogleMatrixApiRequestDistance distance;
    private GoogleMatrixApiRequestDuration duration;
    private String status;

    public GoogleMatrixApiRequestDistance getDistance() {
        return distance;
    }

    public void setDistance(GoogleMatrixApiRequestDistance distance) {
        this.distance = distance;
    }

    public GoogleMatrixApiRequestDuration getDuration() {
        return duration;
    }

    public void setDuration(GoogleMatrixApiRequestDuration duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
