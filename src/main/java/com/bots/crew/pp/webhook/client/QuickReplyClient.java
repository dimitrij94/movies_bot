package com.bots.crew.pp.webhook.client;

import com.bots.crew.pp.webhook.enteties.in.messages.MessageWithQuickReplies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class QuickReplyClient extends AbstractFacebookClient {
    private Logger log = LoggerFactory.getLogger(QuickReplyClient.class);
    private Class<MessageWithQuickReplies> postClass = MessageWithQuickReplies.class;

    public QuickReplyClient(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    public HttpStatus sendQuickReply(MessageWithQuickReplies quickReplyMessage) {
        try {
            restTemplate.postForObject(this.postUrl, quickReplyMessage, postClass);
            return HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString());
            return HttpStatus.BAD_REQUEST;
        }
    }
}
