package dev.piste.vayna.apis.valorantapi.gson.weapon;

import java.util.List;

public class WeaponStats {

    private double fireRate;
    private int magazineSize;
    private double equipTimeSeconds;
    private double reloadTimeSeconds;
    private List<DamageRanges> damageRanges;

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

    public List<DamageRanges> getDamageRanges() {
        return damageRanges;
    }
}
