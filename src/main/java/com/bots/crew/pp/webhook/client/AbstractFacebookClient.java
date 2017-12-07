package com.bots.crew.pp.webhook.client;

import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractFacebookClient {
    protected final String pageAccessKey;
    protected final String postUrl;
    protected Environment env;
    protected RestTemplate restTemplate;

    public AbstractFacebookClient(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
        pageAccessKey = this.env.getProperty("facebook.test.page.access.token");
        postUrl = "https://graph.facebook.com/v2.6/me/messages?access_token=" + this.pageAccessKey;

    }
}
