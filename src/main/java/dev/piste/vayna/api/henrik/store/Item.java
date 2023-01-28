package dev.piste.vayna.api.henrik.store;

import dev.piste.vayna.api.valorantapi.Buddy;
import dev.piste.vayna.api.valorantapi.Playercard;

public class Item {

    private String uuid;
    private String name;
    private String image;
    private String type;
    private int amount;
    private int base_price;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getBasePrice() {
        return base_price;
    }
}
