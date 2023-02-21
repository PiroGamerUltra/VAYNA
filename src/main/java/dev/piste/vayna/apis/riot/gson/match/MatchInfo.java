package dev.piste.vayna.apis.riot.gson.match;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class MatchInfo {

    private String matchId;
    private String mapId;
    private long gameLengthMillis;
    private long gameStartMillis;
    private boolean isCompleted;
    private String queueId;
    private boolean isRanked;
    private String seasonId;

    public String getMatchId() {
        return matchId;
    }

    public String getMapId() {
        return mapId;
    }

    public long getGameLengthMillis() {
        return gameLengthMillis;
    }

    public long getGameStartMillis() {
        return gameStartMillis;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getQueueId() {
        return queueId;
    }

    public boolean isRanked() {
        return isRanked;
    }

    public String getSeasonId() {
        return seasonId;
    }
}
