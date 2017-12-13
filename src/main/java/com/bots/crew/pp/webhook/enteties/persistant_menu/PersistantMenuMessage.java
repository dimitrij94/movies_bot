package com.bots.crew.pp.webhook.enteties.persistant_menu;

import com.bots.crew.pp.webhook.MessangerUserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersistantMenuMessage {
    @JsonProperty("persistent_menu")
    private MessangerUserStatus options;

    public MessangerUserStatus getOptions() {
        return options;
    }

    public void setOptions(MessangerUserStatus options) {
        this.options = options;
    }
}
