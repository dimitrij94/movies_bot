package com.bots.crew.pp.webhook.enteties.properties.persistent_menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersistentMenuRequest {
    @JsonProperty("persistent_menu")
    private List<PersistentMenu> persistentMenu;

    public List<PersistentMenu> getPersistentMenu() {
        return persistentMenu;
    }

    public void setPersistentMenu(List<PersistentMenu> persistentMenu) {
        this.persistentMenu = persistentMenu;
    }
}
