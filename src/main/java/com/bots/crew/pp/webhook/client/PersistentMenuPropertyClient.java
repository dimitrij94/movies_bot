package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.enteties.properties.persistent_menu.MenuItem;
import com.bots.crew.pp.webhook.enteties.properties.persistent_menu.PersistentMenu;
import com.bots.crew.pp.webhook.enteties.properties.persistent_menu.PersistentMenuRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class PersistentMenuPropertyClient extends AbstractFacebookClient<PersistentMenuRequest> {
    private String postUrl = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=" + super.pageAccessKey;

    public PersistentMenuPropertyClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return getDefaultHeaders();
    }

    @Override
    protected String getPostUrl() {
        return postUrl;
    }

    public static PersistentMenuRequest getDefaultRequest() {
        MenuItem item = new MenuItem();
        item.setType("postback");
        item.setPayload(String.format("{\"persistent_menu\":%d}", MessangerUserStatus.SHOW_RESERVATIONS.ordinal()));
        item.setTitle("Show all of my reservation");

        PersistentMenu menu = new PersistentMenu();
        menu.setLocale("default");
        menu.setComposerInputDisabled(false);
        menu.setMenuItemList(Collections.singletonList(item));

        PersistentMenuRequest request = new PersistentMenuRequest();
        request.setPersistentMenu(Collections.singletonList(menu));

        return request;
    }
}
