package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;

public class Bundle {

    private String displayName;
    private String displayIcon;

    public static Bundle get(String uuid) {
        JsonObject jsonObject = HttpRequest.doHenrikApiRequest("https://valorant-api.com/v1/bundles/" + uuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, Bundle.class);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }
}
