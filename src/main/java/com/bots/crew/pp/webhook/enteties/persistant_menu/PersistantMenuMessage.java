package com.bots.crew.pp.webhook.enteties.persistant_menu;

import com.bots.crew.pp.webhook.PersistantMenuOptions;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersistantMenuMessage {
    @JsonProperty("persistent_menu")
    private PersistantMenuOptions options;

    public PersistantMenuOptions getOptions() {
        return options;
    }

    public void setOptions(PersistantMenuOptions options) {
        this.options = options;
    }
}
