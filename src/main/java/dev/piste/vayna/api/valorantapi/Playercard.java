package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;

public class Playercard {

    private String displayName;
    private String smallArt;
    private String wideArt;
    private String largeArt;

    public static Playercard get(String uuid) {
        JsonObject jsonObject = HttpRequest.doHenrikApiRequest("https://valorant-api.com/v1/playercards/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Playercard.class);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSmallArt() {
        return smallArt;
    }

    public String getWideArt() {
        return wideArt;
    }

    public String getLargeArt() {
        return largeArt;
    }
}
