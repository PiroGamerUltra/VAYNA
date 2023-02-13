package dev.piste.vayna.apis.riot.gson.match;

import dev.piste.vayna.apis.riot.gson.match.player.Stats;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class Player {

    private String puuid;
    private String gameName;
    private String tagLine;
    private String teamId;
    private String partyId;
    private String characterId;
    private Stats stats;
    private int competitiveTier;
    private boolean isObserver;
    private String playerCard;
    private String playerTitle;

    public String getPuuid() {
        return puuid;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPartyId() {
        return partyId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public Stats getStats() {
        return stats;
    }

    public int getCompetitiveTier() {
        return competitiveTier;
    }

    public boolean isObserver() {
        return isObserver;
    }

    public String getPlayerCard() {
        return playerCard;
    }

    public String getPlayerTitle() {
        return playerTitle;
    }
}
