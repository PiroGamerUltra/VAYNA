package dev.piste.vayna.apis.riot.gson;

import dev.piste.vayna.apis.riot.gson.match.*;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Match {

    private MatchInfo matchInfo;
    private ArrayList<Player> players;
    private ArrayList<Coach> coaches;
    private ArrayList<Team> teams;
    private ArrayList<RoundResult> roundResults;

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Coach> getCoaches() {
        return coaches;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<RoundResult> getRoundResults() {
        return roundResults;
    }
}
