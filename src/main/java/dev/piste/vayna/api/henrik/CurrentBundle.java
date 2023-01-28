package dev.piste.vayna.api.henrik;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.store.Item;
import dev.piste.vayna.api.valorantapi.Bundle;

import java.util.List;

public class CurrentBundle {

    private String bundle_uuid;
    private int bundle_price;
    private List<Item> items;

    public static List<CurrentBundle> getCurrentBundles() {
        JsonObject jsonObject = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v2/store-featured");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<CurrentBundle>>(){}.getType());
    }

    public int getPrice() {
        return bundle_price;
    }

    public List<Item> getItems() {
        return items;
    }

    public Bundle getBundle() {
        return Bundle.get(bundle_uuid);
    }

}
