package dev.piste.vayna.api.riotgames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveShard {

    private String activeShard;

    public static ActiveShard getByPuuid(String puuid) {
        JsonNode activeShardNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        try {
            ActiveShard activeShard = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(activeShardNode), new TypeReference<ActiveShard>() {});
            return activeShard;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getActiveShard() {
        return activeShard;
    }

    public PlatformData getPlatformData() {
        return PlatformData.getByRegion(activeShard);
    }

}
