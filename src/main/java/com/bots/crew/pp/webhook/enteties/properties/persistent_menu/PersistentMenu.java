package com.bots.crew.pp.webhook.enteties.properties.persistent_menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersistentMenu {

    private String locale;
    @JsonProperty("composer_input_disabled")
    private boolean composerInputDisabled;

    @JsonProperty("call_to_actions")
    List<MenuItem> menuItemList;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isComposerInputDisabled() {
        return composerInputDisabled;
    }

    public void setComposerInputDisabled(boolean composerInputDisabled) {
        this.composerInputDisabled = composerInputDisabled;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }
}
