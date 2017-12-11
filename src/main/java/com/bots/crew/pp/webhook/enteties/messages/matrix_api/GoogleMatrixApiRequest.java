package com.bots.crew.pp.webhook.enteties.messages.matrix_api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleMatrixApiRequest {
    @JsonProperty("destination_address")
    private List<String> destinationAddress;
    @JsonProperty("origin_address")
    private List<String>originAddress;
    private List<GoogleMatrixApiRequestRows> rows;
    private String status;


    public List<String> getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(List<String> destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public List<String> getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(List<String> originAddress) {
        this.originAddress = originAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoogleMatrixApiRequestRows> getRows() {
        return rows;
    }

    public void setRows(List<GoogleMatrixApiRequestRows> rows) {
        this.rows = rows;
    }
}
