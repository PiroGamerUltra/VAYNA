package dev.piste.vayna.apis.riot.gson.match.player;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Stats {

    private int score;
    private int roundsPlayed;
    private int kills;
    private int deaths;
    private int assists;
    private long playtimeMillis;
    private AbilityCasts abilityCasts;

    public int getScore() {
        return score;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public long getPlaytimeMillis() {
        return playtimeMillis;
    }

    public AbilityCasts getAbilityCasts() {
        return abilityCasts;
    }
}
