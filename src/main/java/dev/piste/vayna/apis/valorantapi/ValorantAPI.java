package dev.piste.vayna.apis.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.ApiHttpRequest;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.valorantapi.gson.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.ArrayList;

public class ValorantAPI {

    // Agents
    public static Agent getAgent(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/agents/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Agent.class);
    }

    public static Agent getAgentByName(String name, String languageCode) throws StatusCodeException {
        for(Agent foundAgent : getAgents(languageCode)) {
            if(!foundAgent.isPlayableCharacter()) continue;
            if(foundAgent.getDisplayName().equalsIgnoreCase(name)) return foundAgent;
        }
        return new Agent();
    }

    public static ArrayList<Agent> getAgents(String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/agents?language=" + languageCode);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<Agent>>(){}.getType());
    }

    // Gamemodes
    public static Gamemode getGamemode(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/gamemodes/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Gamemode.class);
    }

    public static Gamemode getGamemodeByName(String name, String languageCode) throws StatusCodeException {
        for(Gamemode foundGamemode : getGamemodes(languageCode)) {
            if(foundGamemode.getDisplayName().equalsIgnoreCase(name)) return foundGamemode;
        }
        return new Gamemode();
    }

    public static ArrayList<Gamemode> getGamemodes(String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/gamemodes?language=" + languageCode);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<Gamemode>>(){}.getType());
    }

    // Maps
    public static Map getMap(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/maps/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Map.class);
    }

    public static Map getMapByName(String name, String languageCode) throws StatusCodeException {
        for(Map foundMap : getMaps(languageCode)) {
            if(foundMap.getDisplayName().equalsIgnoreCase(name)) return foundMap;
        }
        return new Map();
    }

    public static ArrayList<Map> getMaps(String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/maps?language=" + languageCode);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<Map>>(){}.getType());
    }

    // Weapons
    public static Weapon getWeapon(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/weapons/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Weapon.class);
    }

    public static Weapon getWeaponByName(String name, String languageCode) throws StatusCodeException {
        for(Weapon foundWeapon : getWeapons(languageCode)) {
            if(foundWeapon.getDisplayName().equalsIgnoreCase(name)) return foundWeapon;
        }
        return new Weapon();
    }

    public static ArrayList<Weapon> getWeapons(String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/weapons?language=" + languageCode);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<Weapon>>(){}.getType());
    }

    public static Buddy getBuddy(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/buddies/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Buddy.class);
    }

    public static Bundle getBundle(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/bundles/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Bundle.class);
    }

    public static Playercard getPlayercard(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/playercards/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Playercard.class);
    }

    public static Spray getSpray(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/sprays/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Spray.class);
    }

    public static CompetitiveTier getLatestCompetitiveTier() throws StatusCodeException {
        return getCompetitiveTiers().get(getCompetitiveTiers().size()-1);
    }

    public static ArrayList<CompetitiveTier> getCompetitiveTiers() throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/competitivetiers");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<CompetitiveTier>>(){}.getType());
    }

    public static Skin getSkin(String uuid, String languageCode) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://valorant-api.com/v1/weapons/skins/" + uuid + "?language=" + languageCode);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Skin.class);
    }

    private static JsonObject performHttpRequest(String uri) throws StatusCodeException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return new ApiHttpRequest().performHttpRequest(request);
    }

}
