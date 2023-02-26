package dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat;

import dev.piste.vayna.apis.riot.gson.match.roundresult.Location;
import dev.piste.vayna.apis.riot.gson.match.roundresult.PlayerLocation;
import dev.piste.vayna.apis.riot.gson.match.roundresult.playerstat.kill.FinishingDamage;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class Kill {

    private int timeSinceGameStartMillis;
    private int timeSinceRoundStartMillis;
    private String killer;
    private String victim;
    private Location victimLocation;
    private ArrayList<String> assistants;
    private ArrayList<PlayerLocation> playerLocations;
    private FinishingDamage finishingDamage;

    public int getTimeSinceGameStartMillis() {
        return timeSinceGameStartMillis;
    }

    public int getTimeSinceRoundStartMillis() {
        return timeSinceRoundStartMillis;
    }

    public String getKiller() {
        return killer;
    }

    public String getVictim() {
        return victim;
    }

    public Location getVictimLocation() {
        return victimLocation;
    }

    public ArrayList<String> getAssistants() {
        return assistants;
    }

    public ArrayList<PlayerLocation> getPlayerLocations() {
        return playerLocations;
    }

    public FinishingDamage getFinishingDamage() {
        return finishingDamage;
    }
}
