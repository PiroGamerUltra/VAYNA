package dev.piste.vayna.apis.officer.gson.weapon;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
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
