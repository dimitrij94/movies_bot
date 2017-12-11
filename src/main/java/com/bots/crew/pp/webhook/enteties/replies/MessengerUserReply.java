package com.bots.crew.pp.webhook.enteties.replies;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessengerUserReply {
    @JsonProperty("persistant_menu")
    private boolean persitantMenu;

    private String command;

    public boolean isPersitantMenu() {
        return persitantMenu;
    }

    public void setPersitantMenu(boolean persitantMenu) {
        this.persitantMenu = persitantMenu;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
