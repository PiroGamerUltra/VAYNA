package dev.piste.vayna.apis.valorantapi.gson;

import dev.piste.vayna.apis.valorantapi.gson.agent.Ability;
import dev.piste.vayna.apis.valorantapi.gson.agent.Role;

import java.util.List;

// GSON CLASS
@SuppressWarnings("ALL")
public class Agent {

    private String uuid;
    private String displayName;
    private String description;
    private String displayIcon;
    private String fullPortrait;
    private Role role;
    private boolean isPlayableCharacter;
    private List<Ability> abilities;

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getFullPortrait() {
        return fullPortrait;
    }

    public Role getRole() {
        return role;
    }

    public boolean isPlayableCharacter() {
        return isPlayableCharacter;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }
}
