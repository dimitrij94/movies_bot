package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.builders.TextReplyBuilder;
import com.bots.crew.pp.webhook.builders.text.TextReplyBuilderImpl;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TextMessageClient extends MessageClient {
    private TextReplyBuilder textReplyBuilder = new TextReplyBuilderImpl();

    public TextMessageClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    public void sendTextMessage(String psid, String text) {
        textReplyBuilder.setMessage(text);
        textReplyBuilder.setPsid(psid);
        sendMassage(textReplyBuilder.build());
    }
}
