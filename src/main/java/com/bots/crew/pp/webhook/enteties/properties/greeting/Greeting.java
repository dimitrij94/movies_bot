package com.bots.crew.pp.webhook.enteties.properties.greeting;

public class Greeting {
    private String locale;
    private String text;

    public Greeting() {
    }

    public Greeting(String locale, String text) {
        this.locale = locale;
        this.text = text;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
