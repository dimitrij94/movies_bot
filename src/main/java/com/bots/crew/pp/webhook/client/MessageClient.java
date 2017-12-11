package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Primary
public class MessageClient extends AbstractFacebookClient<MessagingRequest> {
    private Logger log = LoggerFactory.getLogger(MessageClient.class);
    private HttpHeaders h = super.getDefaultHeaders();
    private final String postUrl = "https://graph.facebook.com/v2.6/me/messages?access_token=" + this.pageAccessKey;

    public MessageClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return h;
    }

    @Override
    protected String getPostUrl() {
        return postUrl;
    }
}
