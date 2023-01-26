package dev.piste.vayna.api.valorantapi;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.valorantapi.agent.Ability;
import dev.piste.vayna.api.valorantapi.agent.Role;

import java.util.List;

public class Agent {

    private String uuid;
    private String displayName;
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    private Role role;
    private List<Ability> abilities;

    public static Agent getAgentByName(String name) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/agents");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        List<Agent> agentList = new Gson().fromJson(dataArray, new TypeToken<List<Agent>>(){}.getType());
        for(Agent foundAgent : agentList) {
            if(foundAgent.getDisplayName().equalsIgnoreCase(name)) {
                return foundAgent;
            }
        }
        return new Agent();
    }

    public static List<Agent> getAgents() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/agents");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Agent>>(){}.getType());
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDeveloperName() {
        return developerName;
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

    public List<Ability> getAbilities() {
        return abilities;
    }
}
