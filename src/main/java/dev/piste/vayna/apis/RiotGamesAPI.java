package dev.piste.vayna.apis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.apis.entities.riotgames.*;
import dev.piste.vayna.config.ConfigManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class RiotGamesAPI {

    private final String KEY_HEADER_NAME = "X-Riot-Token";
    private final String KEY_HEADER_VALUE = ConfigManager.getTokensConfig().getApiKeys().getRiot();
    private final String RIOT_BASE_URL = "https://europe.api.riotgames.com/riot";
    private final String VAL_BASE_URL = "https://%s.api.riotgames.com/val";
    private final RestClient riotRestClient = new RestClient(RIOT_BASE_URL).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE);

    public RiotAccount getAccount(String name, String tag) throws HttpErrorException, IOException, InterruptedException {
        final String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        final String encodedTag = URLEncoder.encode(tag, StandardCharsets.UTF_8);
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/accounts/by-riot-id/%s/%s", encodedName, encodedTag));
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public RiotAccount getAccount(String puuid) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/accounts/by-puuid/%s", puuid));
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public String getRegion(String puuid) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/active-shards/by-game/val/by-puuid/%s", puuid));
        return jsonObject.get("activeShard").getAsString();
    }

    public PlatformData getPlatformData(String region) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE)
                .doGet("/status/v1/platform-data");
        return new Gson().fromJson(jsonObject, PlatformData.class);
    }

    public MatchList getMatchList(String puuid, String region) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE)
                .doGet(String.format("/match/v1/matchlists/by-puuid/%s", puuid));
        return new Gson().fromJson(jsonObject, MatchList.class);
    }

    public Match getMatch(String matchId, String region) throws HttpErrorException, IOException, InterruptedException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE)
                .doGet(String.format("/match/v1/matches/%s", matchId));
        return new Gson().fromJson(jsonObject, Match.class);
    }

    public Leaderboard getLeaderboard(String actId, int size, int startIndex, String region) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE)
                .doGet(String.format("/ranked/v1/leaderboards/by-act/%s?size=%s&startIndex=%s", actId, size, startIndex));
        return new Gson().fromJson(jsonObject, Leaderboard.class);
    }

}
