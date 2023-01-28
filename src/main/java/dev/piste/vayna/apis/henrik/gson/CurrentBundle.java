package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.store.Item;

import java.util.List;

// GSON CLASS
public class CurrentBundle {

    private String bundle_uuid;
    private int bundle_price;
    private List<Item> items;

    public String getBundleUuid() {
        return bundle_uuid;
    }

    public int getPrice() {
        return bundle_price;
    }

    public List<Item> getItems() {
        return items;
    }

}
