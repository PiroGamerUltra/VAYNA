package dev.piste.vayna.http.apis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.RestClient;
import dev.piste.vayna.http.models.riotgames.Leaderboard;
import dev.piste.vayna.http.models.riotgames.Match;
import dev.piste.vayna.http.models.riotgames.MatchList;
import dev.piste.vayna.http.models.riotgames.PlatformData;
import dev.piste.vayna.util.RiotRegion;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class ValorantAPI {

    private final RestClient restClient;

    public ValorantAPI(RiotRegion region) {
        String KEY_HEADER_NAME = "X-Riot-Token";
        String KEY_HEADER_VALUE = ConfigManager.getTokensConfig().getApiKeys().getRiotGames();
        String BASE_URL = "https://%s.api.riotgames.com/val";
        restClient = new RestClient(String.format(BASE_URL, region.getId())).addHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE);
    }

    public PlatformData getPlatformData() throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet("/status/v1/platform-data");
        return new Gson().fromJson(jsonObject, PlatformData.class);
    }

    public MatchList getMatchList(String puuid) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/match/v1/matchlists/by-puuid/%s", puuid));
        return new Gson().fromJson(jsonObject, MatchList.class);
    }

    public Match getMatch(String matchId) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/match/v1/matches/%s", matchId));
        return new Gson().fromJson(jsonObject, Match.class);
    }

    public Leaderboard getLeaderboard(String actId, int size, int startIndex) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/ranked/v1/leaderboards/by-act/%s?size=%s&startIndex=%s", actId, size, startIndex));
        return new Gson().fromJson(jsonObject, Leaderboard.class);
    }

}