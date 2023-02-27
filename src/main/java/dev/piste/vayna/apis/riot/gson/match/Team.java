package dev.piste.vayna.apis.riot.gson.match;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Team {

    private String teamId;
    private boolean won;
    private int roundsPlayed;
    private int roundsWon;
    private int numPoints;

    public String getTeamId() {
        return teamId;
    }

    public boolean hasWon() {
        return won;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getNumPoints() {
        return numPoints;
    }
}
