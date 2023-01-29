package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.mmr.HighestRank;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;

// GSON CLASS
@SuppressWarnings("ALL")
public class MMR {

    private Rank current_data;
    private HighestRank highest_rank;

    public Rank getRank() {
        return current_data;
    }

    public HighestRank getHighestRank() {
        return highest_rank;
    }
}

