package com.bots.crew.pp.webhook.enteties.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GenericReplyPayload {
    @JsonProperty("template_type")
    private String templateType;

    private List<GenericTamplateElement> elements;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public List<GenericTamplateElement> getElements() {
        return elements;
    }

    public void setElements(List<GenericTamplateElement> elements) {
        this.elements = elements;
    }
}
