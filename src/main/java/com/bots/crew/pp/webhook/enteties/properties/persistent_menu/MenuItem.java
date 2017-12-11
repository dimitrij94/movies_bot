package com.bots.crew.pp.webhook.enteties.properties.persistent_menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MenuItem {
    /**
     * @value field #type takes web one of following values web_url, postback, nested
     */
    private String type;

    private String title;

    private String url;

    private String payload;

    /**
     * @value #menuItems is a list of a submenus nested inside of this menuItem
     */
    @JsonProperty("call_to_actions")
    private List<MenuItem> menuItems;

    @JsonProperty("webview_height_ratio")
    private String webviewHeightRation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getWebviewHeightRation() {
        return webviewHeightRation;
    }

    public void setWebviewHeightRation(String webviewHeightRation) {
        this.webviewHeightRation = webviewHeightRation;
    }
}
