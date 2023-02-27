package dev.piste.vayna.apis.riot.gson.match;

import dev.piste.vayna.apis.riot.gson.match.roundresult.Location;
import dev.piste.vayna.apis.riot.gson.match.roundresult.PlayerLocation;
import dev.piste.vayna.apis.riot.gson.match.roundresult.PlayerStat;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class RoundResult {

    private int roundNum;
    private String roundResult;
    private String roundCeremony;
    private String winningTeam;
    private String bombPlanter;
    private String bombDefuser;
    private int plantRoundTime;
    private ArrayList<PlayerLocation> plantPlayerLocations;
    private Location plantLocation;
    private String plantSite;
    private int defuseRoundTime;
    private ArrayList<PlayerLocation> defusePlayerLocations;
    private Location defuseLocation;
    private ArrayList<PlayerStat> playerStats;
    private String roundResultCode;

    public int getRoundNum() {
        return roundNum;
    }

    public String getRoundResult() {
        return roundResult;
    }

    public String getRoundCeremony() {
        return roundCeremony;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public String getBombPlanter() {
        return bombPlanter;
    }

    public String getBombDefuser() {
        return bombDefuser;
    }

    public int getPlantRoundTime() {
        return plantRoundTime;
    }

    public ArrayList<PlayerLocation> getPlantPlayerLocations() {
        return plantPlayerLocations;
    }

    public Location getPlantLocation() {
        return plantLocation;
    }

    public String getPlantSite() {
        return plantSite;
    }

    public int getDefuseRoundTime() {
        return defuseRoundTime;
    }

    public ArrayList<PlayerLocation> getDefusePlayerLocations() {
        return defusePlayerLocations;
    }

    public Location getDefuseLocation() {
        return defuseLocation;
    }

    public ArrayList<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public String getRoundResultCode() {
        return roundResultCode;
    }

}
