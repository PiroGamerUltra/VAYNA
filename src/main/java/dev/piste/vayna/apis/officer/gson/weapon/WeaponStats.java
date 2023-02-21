package dev.piste.vayna.apis.officer.gson.weapon;

import java.util.ArrayList;

// GSON CLASS
@SuppressWarnings("ALL")
public class WeaponStats {

    private double fireRate;
    private int magazineSize;
    private double equipTimeSeconds;
    private double reloadTimeSeconds;
    private ArrayList<DamageRanges> damageRanges;

    public double getFireRate() {
        return fireRate;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public double getEquipTimeSeconds() {
        return equipTimeSeconds;
    }

    public double getReloadTimeSeconds() {
        return reloadTimeSeconds;
    }

    public ArrayList<DamageRanges> getDamageRanges() {
        return damageRanges;
    }
}
