package com.bots.crew.pp.webhook.enteties.request;

import com.bots.crew.pp.webhook.enteties.payload.AttachmentDefaultAction;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GenericTamplateElement {
    private String title;
    @JsonProperty("image_url")
    private String imageUrl;
    private String subtitle;
    @JsonProperty("default_action")
    private AttachmentDefaultAction defaultAction;
    private List<GenericTamplateButtons> buttons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public AttachmentDefaultAction getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(AttachmentDefaultAction defaultAction) {
        this.defaultAction = defaultAction;
    }

    public List<GenericTamplateButtons> getButtons() {
        return buttons;
    }

    public void setButtons(List<GenericTamplateButtons> buttons) {
        this.buttons = buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericTamplateElement that = (GenericTamplateElement) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (subtitle != null ? !subtitle.equals(that.subtitle) : that.subtitle != null) return false;
        if (defaultAction != null ? !defaultAction.equals(that.defaultAction) : that.defaultAction != null)
            return false;
        return buttons != null ? buttons.equals(that.buttons) : that.buttons == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + (defaultAction != null ? defaultAction.hashCode() : 0);
        result = 31 * result + (buttons != null ? buttons.hashCode() : 0);
        return result;
    }
}
