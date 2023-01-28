package dev.piste.vayna.apis.henrik.gson.mmr;

import dev.piste.vayna.apis.henrik.gson.mmr.rank.Images;

// GSON CLASS
public class Rank {

    private int currenttier;
    private String currenttierpatched;
    private Images images;
    private int ranking_in_tier;
    private int mmr_change_to_last_game;
    private int elo;
    private int games_needed_for_rating;

    public int getCurrentTier() {
        return currenttier;
    }

    public String getCurrentTierPatched() {
        return currenttierpatched;
    }

    public Images getImages() {
        return images;
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

    public int getGamesNeededForRating() {
        return games_needed_for_rating;
    }

}
