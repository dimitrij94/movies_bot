package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractFacebookClient<T> {
    protected final String pageAccessKey;
    protected Environment env;
    protected RestTemplate restTemplate;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public AbstractFacebookClient(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        pageAccessKey = this.env.getProperty("facebook.test.page.access.token");
    }

    public void sendMassage(T request) {
        try {
            restTemplate.postForObject(
                    getPostUrl(),
                    new HttpEntity<>(request, getHeaders()),
                    MessagingRequest.class
            );
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString(), e);
        }
    }

    protected abstract HttpHeaders getHeaders();

    protected abstract String getPostUrl();

    protected HttpHeaders getDefaultHeaders(){
        HttpHeaders h = new HttpHeaders();
        h.set("Content-type", "application/json");
        return h;
    }
}
