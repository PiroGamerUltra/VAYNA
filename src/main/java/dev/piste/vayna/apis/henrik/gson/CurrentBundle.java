package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.store.Item;

import java.util.ArrayList;

// GSON CLASS
@SuppressWarnings("ALL")
public class CurrentBundle {

    private String bundle_uuid;
    private int bundle_price;
    private ArrayList<Item> items;
    private int seconds_remaining;

    public String getBundleUuid() {
        return bundle_uuid;
    }

    public int getPrice() {
        return bundle_price;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getSecondsRemaining() {
        return seconds_remaining;
    }
}
