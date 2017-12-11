package com.bots.crew.pp.webhook.enteties.messages.matrix_api;

import java.util.List;

public class GoogleMatrixApiRequestRows {
    private List<GoogleMatrixApiRequestElements> elements;

    public List<GoogleMatrixApiRequestElements> getElements() {
        return elements;
    }

    public void setElements(List<GoogleMatrixApiRequestElements> elements) {
        this.elements = elements;
    }
}
