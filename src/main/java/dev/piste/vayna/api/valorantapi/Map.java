package dev.piste.vayna.api.valorantapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Map {

    private String uuid;
    private String displayName;
    private String coordinates;
    private String displayIcon;
    private String listViewIcon;
    private String splash;

    public static Map getMapByName(String name) {
        JsonNode mapsNode = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/maps");
        JsonNode dataNode = mapsNode.get("data");
        try {
            List<Map> mapList = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(dataNode), new TypeReference<List<Map>>() {});
            Map map = new Map();
            for(Map foundMap : mapList) {
                if(foundMap.getDisplayName().equalsIgnoreCase(name)) {
                    map = foundMap;
                }
            }
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map> getMaps() {
        JsonNode mapsNode = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/maps");
        JsonNode dataNode = mapsNode.get("data");
        try {
            return Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(dataNode), new TypeReference<List<Map>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getSplash() {
        return splash;
    }

    public String getListViewIcon() {
        return listViewIcon;
    }
}
