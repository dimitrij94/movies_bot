package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.properties.greeting.Greeting;
import com.bots.crew.pp.webhook.enteties.properties.greeting.GreetingPropertyRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Component
public class GreetingPropertyMessageClient extends AbstractFacebookClient<GreetingPropertyRequest> {
    private String botsProfileUrl = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=" + this.pageAccessKey;
    private HttpHeaders h = super.getDefaultHeaders();

    public GreetingPropertyMessageClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return h;
    }

    @Override
    protected String getPostUrl() {
        return botsProfileUrl;
    }

    public void configureBotsGreeting(GreetingPropertyRequest request) {
        this.sandMassage(request);
    }

    public static GreetingPropertyRequest getDefault() {
        GreetingPropertyRequest request = new GreetingPropertyRequest();
        List<Greeting> defaultGreetings = new LinkedList<>();
        defaultGreetings.add(new Greeting("default", "Давйте розпочинати!"));
        defaultGreetings.add(new Greeting("uk_UA", "Давйте розпочинати!"));
        defaultGreetings.add(new Greeting("en_US", "Let`s get started!"));
        request.setGreeting(defaultGreetings);
        return request;
    }

}
