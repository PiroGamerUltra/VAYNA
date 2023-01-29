package dev.piste.vayna.apis.henrik.gson;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class MMRHistory {

    private int currenttier;
    private String currenttierpatched;
    private int ranking_in_tier;
    private int mmr_change_to_last_game;
    private int elo;
    private long date_raw;

    public int getCurrentTier() {
        return currenttier;
    }

    public String getCurrentTierPatched() {
        return currenttierpatched;
    }

    public int getRankingInTier() {
        return ranking_in_tier;
    }

    public int getMmrChangeToLastGame() {
        return mmr_change_to_last_game;
    }

    public int getElo() {
        return elo;
    }

    public long getDateRaw() {
        return date_raw;
    }
}
