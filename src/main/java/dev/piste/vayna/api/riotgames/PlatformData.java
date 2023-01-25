package dev.piste.vayna.api.riotgames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformData {

    private String name;

    public static PlatformData getByRegion(String region) {
        JsonNode platformDataNode = HttpRequest.doRiotApiRequest("https://" + region + ".api.riotgames.com/val/status/v1/platform-data");
        try {
            PlatformData platformData = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(platformDataNode), new TypeReference<PlatformData>() {});
            return platformData;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
}
