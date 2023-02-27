package dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Economy {

    private int loadoutValue;
    private String weapon;
    private String armor;
    private int remaining;
    private int spent;

    public int getLoadoutValue() {
        return loadoutValue;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getArmor() {
        return armor;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getSpent() {
        return spent;
    }
}
