package com.bots.crew.pp.webhook.builders.generic_reply;

import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateButtons;
import com.bots.crew.pp.webhook.enteties.payload.GenericTamplateElement;

import java.util.List;

public abstract class GenericReplyElementsBuilder {
    public GenericTamplateElement build() {
        GenericTamplateElement element = new GenericTamplateElement();

        element.setTitle(getTitle());
        element.setSubtitle(getSubtitle());
        element.setImageUrl(getImageUrl());
        //elementOne.setDefaultAction(getDefaultActions());
        element.setButtons(getGenericTamplateButtons());
        return element;
    }

    protected abstract String getTitle();

    protected abstract String getSubtitle();

    protected abstract String getImageUrl();

    protected abstract List<GenericTamplateButtons> getGenericTamplateButtons();

}
