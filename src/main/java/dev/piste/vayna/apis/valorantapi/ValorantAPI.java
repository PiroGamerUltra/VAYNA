package dev.piste.vayna.apis.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.HttpRequest;
import dev.piste.vayna.apis.valorantapi.gson.*;

import java.util.List;

public class ValorantAPI {

    // Agents
    public static Agent getAgentByName(String name) {
        for(Agent foundAgent : getAgents()) {
            if(!foundAgent.isPlayableCharacter()) continue;
            if(foundAgent.getDisplayName().equalsIgnoreCase(name)) return foundAgent;
        }
        return new Agent();
    }

    public static List<Agent> getAgents() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/agents");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Agent>>(){}.getType());
    }

    // Gamemodes
    public static Gamemode getGamemodeByName(String name) {
        for(Gamemode foundGamemode : getGamemodes()) {
            if(foundGamemode.getDisplayName().equalsIgnoreCase(name)) return foundGamemode;
        }
        return new Gamemode();
    }

    public static List<Gamemode> getGamemodes() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/gamemodes");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Gamemode>>(){}.getType());
    }

    // Maps
    public static Map getMapByName(String name) {
        for(Map foundMap : getMaps()) {
            if(foundMap.getDisplayName().equalsIgnoreCase(name)) return foundMap;
        }
        return new Map();
    }

    public static List<Map> getMaps() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/maps");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Map>>(){}.getType());
    }

    // Weapons
    public static Weapon getWeaponByName(String name) {
        for(Weapon foundWeapon : getWeapons()) {
            if(foundWeapon.getDisplayName().equalsIgnoreCase(name)) return foundWeapon;
        }
        return new Weapon();
    }

    public static List<Weapon> getWeapons() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/weapons");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Weapon>>(){}.getType());
    }

    public static Buddy getBuddy(String uuid) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/buddies/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Buddy.class);
    }

    public static Bundle getBundle(String uuid) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/bundles/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Bundle.class);
    }

    public static Playercard getPlayercard(String uuid) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/playercards/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Playercard.class);
    }

    public static Spray getSpray(String uuid) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/sprays/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Spray.class);
    }

}
