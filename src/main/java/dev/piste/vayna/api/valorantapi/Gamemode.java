package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.api.HttpRequest;

import java.util.List;

public class Gamemode {

    private String displayName;
    private String duration;
    private String displayIcon;

    public static Gamemode getGamemodeByName(String name) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/gamemodes");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        List<Gamemode> gamemodeList = new Gson().fromJson(dataArray, new TypeToken<List<Gamemode>>(){}.getType());
        for(Gamemode foundGamemode : gamemodeList) {
            if(foundGamemode.getDisplayName().equalsIgnoreCase(name)) {
                return foundGamemode;
            }
        }
        return new Gamemode();
    }

    public static List<Gamemode> getGamemodes() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/gamemodes");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Gamemode>>(){}.getType());
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDuration() {
        return duration;
    }
}
