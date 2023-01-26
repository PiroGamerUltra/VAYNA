package dev.piste.vayna.api.valorantapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.valorantapi.weapon.ShopData;
import dev.piste.vayna.api.valorantapi.weapon.WeaponStats;

import java.util.List;

public class Weapon {

    private String displayName;
    private String displayIcon;
    private WeaponStats weaponStats;
    private ShopData shopData;

    public static Weapon getWeaponByName(String name) {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/weapons");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        List<Weapon> weaponList = new Gson().fromJson(dataArray, new TypeToken<List<Weapon>>(){}.getType());
        for(Weapon foundWeapon : weaponList) {
            if(foundWeapon.getDisplayName().equalsIgnoreCase(name)) {
                return foundWeapon;
            }
        }
        return new Weapon();
    }

    public static List<Weapon> getWeapons() {
        JsonObject jsonObject = HttpRequest.doValorantApiRequest("https://valorant-api.com/v1/weapons");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<List<Weapon>>(){}.getType());
    }

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
