package dev.piste.vayna.apis.riot.gson;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("ALL")
public class MatchListEntry {

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
