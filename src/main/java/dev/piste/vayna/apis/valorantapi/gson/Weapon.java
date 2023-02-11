package dev.piste.vayna.apis.valorantapi.gson;

import dev.piste.vayna.apis.valorantapi.gson.weapon.ShopData;
import dev.piste.vayna.apis.valorantapi.gson.weapon.WeaponStats;

// GSON CLASS
@SuppressWarnings("ALL")
public class Weapon {

    private String uuid;
    private String displayName;
    private String displayIcon;
    private String killStreamIcon;
    private WeaponStats weaponStats;
    private ShopData shopData;

    public String getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getKillStreamIcon() {
        return killStreamIcon;
    }

    public WeaponStats getWeaponStats() {
        return weaponStats;
    }

    public ShopData getShopData() {
        return shopData;
    }

}
