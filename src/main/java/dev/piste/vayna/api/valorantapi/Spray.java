package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;

public class Spray {

    private String displayName;
    private String fullTransparentIcon;
    private String animationGif;

    public static Spray get(String uuid) {
        JsonObject jsonObject = HttpRequest.doHenrikApiRequest("https://valorant-api.com/v1/sprays/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Spray.class);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFullTransparentIcon() {
        return fullTransparentIcon;
    }

    public String getAnimationGif() {
        return animationGif;
    }

}
