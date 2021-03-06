package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.messages.matrix_api.GoogleMatrixApiMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleMatrixApiClient {
    private RestTemplate restTemplate;
    private Logger log = LoggerFactory.getLogger(GoogleMatrixApiClient.class);

    public GoogleMatrixApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleMatrixApiMessage getForDistance(String url) {
        try {
            return this.restTemplate.getForObject(url, GoogleMatrixApiMessage.class);
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString(), e);
            return null;
        }
    }
}
