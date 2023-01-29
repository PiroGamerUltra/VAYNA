package dev.piste.vayna.apis.valorantapi.gson.weapon;

// GSON CLASS
@SuppressWarnings("ALL")
public class DamageRanges {

    private int rangeStartMeters;
    private int rangeEndMeters;
    private double headDamage;
    private double bodyDamage;
    private double legDamage;

    public int getRangeStartMeters() {
        return rangeStartMeters;
    }

    public int getRangeEndMeters() {
        return rangeEndMeters;
    }

    public double getHeadDamage() {
        return headDamage;
    }

    public double getBodyDamage() {
        return bodyDamage;
    }

    public double getLegDamage() {
        return legDamage;
    }


}
