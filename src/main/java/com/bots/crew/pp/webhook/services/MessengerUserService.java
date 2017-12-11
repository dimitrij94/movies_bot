package com.bots.crew.pp.webhook.services;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
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

    public MessengerUser save(String psid, MessangerUserStatus status) {
        return this.repository.save(new MessengerUser(psid, status));
    }

    public MessangerUserStatus getStatus(String psid) {
        MessengerUser user = this.find(psid);
        return user.getStatus();
    }

    public void setStatus(String psid, MessangerUserStatus status) {
        save(psid, status);
        MessangerUserStatus newStatus = find(psid).getStatus();
        assert newStatus.equals(status);
    }

    public MessengerUser storeInSessionPSID(HttpServletRequest request, JsonNode messaging) {
        MessengerUser storedUser = null;
        String psid = (String) request.getAttribute("PSID");
        if (psid == null) {
            psid = messaging.path("sender").path("id").asText();
            storedUser = this.find(psid);
            if (storedUser == null) {
                storedUser = save(psid, MessangerUserStatus.GETTING_STARTED);
            }
        }
        request.setAttribute("PSID", psid);
        return storedUser;
    }

    public MessengerUser find(String psid) {
        return repository.findOne(psid);
    }
}
