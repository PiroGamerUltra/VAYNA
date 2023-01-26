package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.api.HttpRequest;

import java.util.List;

public class Map {

    private String uuid;
    private String displayName;
    private String coordinates;
    private String displayIcon;
    private String listViewIcon;
    private String splash;

    public static Map getMapByName(String name) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/maps");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        List<Map> mapList = new Gson().fromJson(dataArray, new TypeToken<List<Map>>(){}.getType());
        for(Map foundMap : mapList) {
            if(foundMap.getDisplayName().equalsIgnoreCase(name)) {
                return foundMap;
            }
        }
        return new Map();
    }

    public static List<Map> getMaps() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/maps");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Map>>(){}.getType());
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
