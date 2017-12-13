package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.properties.get_started.GetStarted;
import com.bots.crew.pp.webhook.enteties.properties.get_started.GetStartedRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GettingStartedPropertyClient extends AbstractFacebookClient<GetStartedRequest> {
    HttpHeaders h = super.getDefaultHeaders();
    String postUrl = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=" + super.pageAccessKey;

    public GettingStartedPropertyClient(Environment env, RestTemplate restTemplate) {
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

    public static GetStartedRequest getDefault() {
        GetStartedRequest request = new GetStartedRequest();
        GetStarted getStarted = new GetStarted();
        getStarted.setPayload("{\"gettingStarted\":true}");
        request.setGetStarted(getStarted);
        return request;
    }
}
