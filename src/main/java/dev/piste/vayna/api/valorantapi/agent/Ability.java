package dev.piste.vayna.api.valorantapi.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ability {

    private String slot;
    private String displayName;
    private String description;
    private String displayIcon;

    public String getSlot() {
        return slot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

}