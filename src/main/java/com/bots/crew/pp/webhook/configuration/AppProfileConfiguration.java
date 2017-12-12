package com.bots.crew.pp.webhook.configuration;

import com.bots.crew.pp.webhook.client.GettingStartedPropertyClient;
import com.bots.crew.pp.webhook.client.GreetingPropertyMessageClient;
import com.bots.crew.pp.webhook.client.PersistentMenuPropertyClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component("appProfileConfiguration")
public class AppProfileConfiguration implements InitializingBean {
    private GettingStartedPropertyClient gettingStarted;
    private GreetingPropertyMessageClient greeting;
    private PersistentMenuPropertyClient persistentMenuPropertyClient;

    public AppProfileConfiguration(GettingStartedPropertyClient gettingStarted, GreetingPropertyMessageClient greeting, PersistentMenuPropertyClient persistentMenuPropertyClient) {
        this.gettingStarted = gettingStarted;
        this.greeting = greeting;
        this.persistentMenuPropertyClient = persistentMenuPropertyClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //setProperties();
    }

    public void setProperties() {
        gettingStarted.sendMassage(GettingStartedPropertyClient.getDefault());
        greeting.sendMassage(GreetingPropertyMessageClient.getDefault());
        persistentMenuPropertyClient.sendMassage(PersistentMenuPropertyClient.getDefaultRequest());
    }
}
