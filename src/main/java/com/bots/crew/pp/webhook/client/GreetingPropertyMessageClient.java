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
        this.sendMassage(request);
    }

    public static GreetingPropertyRequest getDefault() {
        GreetingPropertyRequest request = new GreetingPropertyRequest();
        List<Greeting> defaultGreetings = new LinkedList<>();
        defaultGreetings.add(new Greeting("default", "Hi, Dmitrij! My name is Isaac The Cinema Bot. I will help you choose a movie and book tickets!"));
        defaultGreetings.add(new Greeting("uk_UA", "Привіт {{user_first_name}}! Моє ім'я Ісаак Бот Кінознавець. Я допоможу тобі вибрати фільм і замовити квиток!"));
        defaultGreetings.add(new Greeting("en_US", "Hi, {{user_first_name}}! My name is Isaac The Cinema Bot. I will help you choose a movie and book tickets!"));
        request.setGreeting(defaultGreetings);
        return request;
    }

}
