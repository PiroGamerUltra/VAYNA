package dev.piste.vayna.apis.riot.gson.match.roundresult;

import dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.Ability;
import dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.Damage;
import dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.Economy;
import dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.Kill;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class PlayerStat {

    private String puuid;
    private ArrayList<Kill> kills;
    private ArrayList<Damage> damage;
    private int score;
    private Economy economy;
    private Ability ability;

    public String getPuuid() {
        return puuid;
    }

    public ArrayList<Kill> getKills() {
        return kills;
    }

    public ArrayList<Damage> getDamage() {
        return damage;
    }

    public int getScore() {
        return score;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Ability getAbility() {
        return ability;
    }
}
