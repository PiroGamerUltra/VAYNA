package dev.piste.vayna.api.valorantapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.valorantapi.agent.Ability;
import dev.piste.vayna.api.valorantapi.agent.Role;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent {

    private String uuid;
    private String displayName;
    private String description;
    private String developerName;
    private String displayIcon;
    private String fullPortrait;
    private String background;
    private Role role;
    private List<Ability> abilities;

    public static Agent getAgentByName(String name) {
        JsonNode mapsNode = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/agents");
        JsonNode dataNode = mapsNode.get("data");
        try {
            List<Agent> agentList = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(dataNode), new TypeReference<List<Agent>>() {});
            Agent agent = new Agent();
            for(Agent foundAgent : agentList) {
                if(foundAgent.getDisplayName().equalsIgnoreCase(name)) {
                    agent = foundAgent;
                }
            }
            return agent;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Agent> getAgents() {
        JsonNode mapsNode = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/agents");
        JsonNode dataNode = mapsNode.get("data");
        try {
            return Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(dataNode), new TypeReference<List<Agent>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUuid() {
        return uuid;
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

    public String getBackground() {
        return background;
    }

    public Role getRole() {
        return role;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }
}
