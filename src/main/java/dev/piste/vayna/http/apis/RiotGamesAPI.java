package dev.piste.vayna.http.apis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.http.RestClient;
import dev.piste.vayna.http.models.riotgames.*;
import dev.piste.vayna.config.ConfigManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class RiotGamesAPI {

    private final RestClient riotRestClient;

    public RiotGamesAPI() {
        String KEY_HEADER_NAME = "X-Riot-Token";
        String KEY_HEADER_VALUE = ConfigManager.getTokensConfig().getApiKeys().getRiotGames();
        String BASE_URL = "https://europe.api.riotgames.com/riot";
        riotRestClient = new RestClient(BASE_URL).addHeader(KEY_HEADER_NAME, KEY_HEADER_VALUE);
    }

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

}
