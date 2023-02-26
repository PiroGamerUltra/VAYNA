package dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class Damage {

    private String receiver;
    private int damage;
    private int legshots;
    private int bodyshots;
    private int headshots;

    public String getReceiver() {
        return receiver;
    }

    public int getDamage() {
        return damage;
    }

    public int getLegshots() {
        return legshots;
    }

    public int getBodyshots() {
        return bodyshots;
    }

    public int getHeadshots() {
        return headshots;
    }
}
