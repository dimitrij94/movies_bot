package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import com.bots.crew.pp.webhook.enteties.messages.Messaging;
import com.bots.crew.pp.webhook.repositories.PsidRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MessengerUserService {
    private PsidRepository repository;

    public MessengerUserService(PsidRepository repository) {
        this.repository = repository;
    }

    public MessengerUser save(String psid, MessangerUserStatus status, MessangerUserStatus previousStatus) {
        return this.repository.save(new MessengerUser(psid, status, previousStatus));
    }

    public MessangerUserStatus getStatus(String psid) {
        MessengerUser user = this.find(psid);
        return user.getStatus();
    }

    public MessengerUser setStatus(String psid, MessangerUserStatus nextStatus, MessangerUserStatus currentStatus) {
        MessengerUser user = new MessengerUser();
        user.setPsid(psid);
        user.setStatus(nextStatus);
        user.setPreviousStatus(currentStatus);
        return this.repository.save(user);
    }


    public MessengerUser setStatus(MessengerUser user, MessangerUserStatus nextStatus, MessangerUserStatus currentStatus) {
        user.setStatus(nextStatus);
        user.setPreviousStatus(currentStatus);
        return this.repository.save(user);
    }

    public MessengerUser createIfNotExists(Messaging messaging) {
        String psid = messaging.getSender().getId();
        MessengerUser storedUser = this.find(psid);
        if (storedUser == null) {
            storedUser = save(psid, MessangerUserStatus.GETTING_STARTED, MessangerUserStatus.GETTING_STARTED);
        }
        return storedUser;
    }

    public MessengerUser createUserIfNotExists(HttpServletRequest request, JsonNode messaging) {
        MessengerUser storedUser = null;
        String psid = (String) request.getAttribute("PSID");
        if (psid == null) {
            psid = messaging.path("sender").path("id").asText();
            storedUser = this.find(psid);
            if (storedUser == null) {
                storedUser = save(psid, MessangerUserStatus.GETTING_STARTED, MessangerUserStatus.GETTING_STARTED);
            }
        }
        request.setAttribute("PSID", psid);
        return storedUser;
    }

    public MessengerUser find(String psid) {
        return repository.findOne(psid);
    }
}
