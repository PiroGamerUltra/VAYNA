package dev.piste.vayna.apis.riot.gson;

import dev.piste.vayna.apis.riot.gson.matchlist.Match;

import java.util.ArrayList;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class Matchlist {

    private String puuid;
    private ArrayList<Match> history;

    public String getPuuid() {
        return puuid;
    }

    public ArrayList<Match> getHistory() {
        return history;
    }
}
