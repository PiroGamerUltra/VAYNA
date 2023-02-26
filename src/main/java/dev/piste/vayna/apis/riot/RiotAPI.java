package dev.piste.vayna.apis.riot;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.RestClient;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.gson.*;
import dev.piste.vayna.config.ConfigManager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RiotAPI {

    private final String KEY_HEADER_NAME = "X-Riot-Token";
    private final String KEY_HEADER_VALUE = ConfigManager.getTokensConfig().getApiKeys().getRiot();
    private final String RIOT_BASE_URL = "https://europe.api.riotgames.com/riot";
    private final String VAL_BASE_URL = "https://%s.api.riotgames.com/val";
    private final RestClient riotRestClient = new RestClient(RIOT_BASE_URL).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE);

    public RiotAccount getAccount(String gameName, String tagLine) throws HttpErrorException {
        final String encodedGameName = URLEncoder.encode(gameName, StandardCharsets.UTF_8);
        final String encodedTagLine = URLEncoder.encode(tagLine, StandardCharsets.UTF_8);
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/accounts/by-riot-id/%s/%s", encodedGameName, encodedTagLine));
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public RiotAccount getAccount(String puuid) throws HttpErrorException {
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/accounts/by-puuid/%s", puuid));
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public String getRegion(String puuid) throws HttpErrorException {
        JsonObject jsonObject = riotRestClient.doGet(String.format("/account/v1/active-shards/by-game/val/by-puuid/%s", puuid));
        return jsonObject.get("activeShard").getAsString();
    }

    public String getRegionName(String activeShard) throws HttpErrorException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, activeShard)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE).doGet("/status/v1/platform-data");
        return jsonObject.get("name").getAsString();
    }

    public ArrayList<MatchListEntry> getMatchList(String puuid, String region) throws HttpErrorException {
        JsonArray jsonArray = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE).doGet(String.format("/match/v1/matchlists/by-puuid/%s", puuid)).getAsJsonArray("history");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<MatchListEntry>>(){}.getType());
    }

    public Match getMatch(String matchId, String region) throws HttpErrorException {
        JsonObject jsonObject = new RestClient(String.format(VAL_BASE_URL, region)).appendHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE).doGet(String.format("/match/v1/matches/%s", matchId));
        return new Gson().fromJson(jsonObject, Match.class);
    }

}
