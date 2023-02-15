package dev.piste.vayna.apis.riot.gson.matchlist;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class ListedMatch {

    private String matchId;
    private long gameStartTimeMillis;
    private String queueId;

    public String getMatchId() {
        return matchId;
    }

    public long getGameStartTimeMillis() {
        return gameStartTimeMillis;
    }

    public String getQueueId() {
        return queueId;
    }
}
