package com.bots.crew.pp.webhook.observers;

import com.bots.crew.pp.webhook.FacebookWebhook;
import com.bots.crew.pp.webhook.MessagerUserStatus;
import com.bots.crew.pp.webhook.builders.quick_reply.FindOrListMoviesQuickRequestBuilder;
import com.bots.crew.pp.webhook.client.TextMessageClient;
import com.bots.crew.pp.webhook.enteties.request.MessagingRequest;
import com.bots.crew.pp.webhook.handlers.MessagingHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ListOrFindMovieObserver implements Observer<MessagingRequest>, InitializingBean {

    private FacebookWebhook webhook;
    private TextMessageClient client;
    private MessagingHandler handler;

    public ListOrFindMovieObserver(FacebookWebhook webhook, MessagingHandler handler) {
        this.handler = handler;
        this.webhook = webhook;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handler.addObserver(this);
    }

    @Override
    public void notify(MessagingRequest message) {
        if (webhook.getStatus() == MessagerUserStatus.GETTING_STARTED || message.getMessage().getText().equals("START")) {
            String psid = webhook.getPSID();
            MessagingRequest request =
                    new FindOrListMoviesQuickRequestBuilder(psid).build();
            client.sandMassage(request);
            this.webhook.setStatus(MessagerUserStatus.SELECT_METHOD_OF_FINDING_MOVIE);
        }
    }
}
