package dev.piste.vayna.apis.valorantapi.gson;

import dev.piste.vayna.apis.valorantapi.gson.weapon.ShopData;
import dev.piste.vayna.apis.valorantapi.gson.weapon.WeaponStats;

// GSON CLASS
@SuppressWarnings("ALL")
public class Weapon {

    private String displayName;
    private String displayIcon;
    private WeaponStats weaponStats;
    private ShopData shopData;

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public WeaponStats getWeaponStats() {
        return weaponStats;
    }

    public ShopData getShopData() {
        return shopData;
    }

}
