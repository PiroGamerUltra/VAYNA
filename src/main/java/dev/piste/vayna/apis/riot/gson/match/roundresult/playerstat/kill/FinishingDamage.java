package dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.kill;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class FinishingDamage {

    private String damageType;
    private String damageItem;
    private boolean isSecondaryFireMode;

    public String getDamageType() {
        return damageType;
    }

    public String getDamageItem() {
        return damageItem;
    }

    public boolean isSecondaryFireMode() {
        return isSecondaryFireMode;
    }
}
