package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.mmr.HighestRank;
import dev.piste.vayna.apis.henrik.gson.mmr.Rank;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class MMR {

    private String name;
    private String tag;
    private Rank current_data;
    private HighestRank highest_rank;

    public String getGameName() {
        return name;
    }

    public String getTagLine() {
        return tag;
    }

    public Rank getRank() {
        return current_data;
    }

    public HighestRank getHighestRank() {
        return highest_rank;
    }
}
