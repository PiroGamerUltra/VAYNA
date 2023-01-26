package dev.piste.vayna.api.riotgames;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;

public class PlatformData {

    private String name;

    public static PlatformData getByRegion(String region) {
        JsonObject jsonObject = HttpRequest.doRiotApiRequest("https://" + region + ".api.riotgames.com/val/status/v1/platform-data");
        PlatformData platformData = new Gson().fromJson(jsonObject, PlatformData.class);
        return platformData;
    }

    public String getName() {
        return name;
    }
}
