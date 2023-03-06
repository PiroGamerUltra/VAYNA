package dev.piste.vayna.apis.riot.gson.match;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class MatchInfo {

    private String matchId;
    private String mapId;
    private String gameVersion;
    private long gameLengthMillis;
    private String region;
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

    public String getGameVersion() {
        return gameVersion;
    }

    public long getGameLengthMillis() {
        return gameLengthMillis;
    }

    public String getRegion() {
        return region;
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
