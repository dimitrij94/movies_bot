package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class TextMessageClient extends AbstractFacebookClient{
    private Logger log = LoggerFactory.getLogger(TextMessageClient.class);
    private HttpHeaders h;

    public TextMessageClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
        h = new HttpHeaders();
        h.set("Content-type", "application/json");
    }

    public void sandMassage(MessagingRequest request) {
        try {
            restTemplate.postForObject(
                    postUrl,
                    new HttpEntity<>(request, h),
                    MessagingRequest.class
            );
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString(), e);
        }
    }
}
