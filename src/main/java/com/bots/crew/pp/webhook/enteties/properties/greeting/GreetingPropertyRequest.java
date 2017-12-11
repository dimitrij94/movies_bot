package com.bots.crew.pp.webhook.enteties.properties.greeting;

import java.util.List;

public class GreetingPropertyRequest {
    private List<Greeting> greeting;

    public List<Greeting> getGreeting() {
        return greeting;
    }

    public void setGreeting(List<Greeting> greeting) {
        this.greeting = greeting;
    }
}
