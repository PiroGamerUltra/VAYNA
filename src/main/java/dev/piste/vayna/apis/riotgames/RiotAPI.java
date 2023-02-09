package dev.piste.vayna.apis.riotgames;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.apis.ApiHttpRequest;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.riotgames.gson.ActiveShard;
import dev.piste.vayna.apis.riotgames.gson.PlatformData;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;
import dev.piste.vayna.config.ConfigManager;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class RiotAPI {

    public static RiotAccount getAccountByRiotId(String gameName, String tagLine) throws InvalidRiotIdException, StatusCodeException {
        try {
            JsonObject jsonObject = performHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
            return new Gson().fromJson(jsonObject, RiotAccount.class);
        } catch (StatusCodeException e) {
            String[] message = e.getMessage().split(" ");
            int statusCode = Integer.parseInt(message[0]);
            if(statusCode == 400 || statusCode == 404) {
                throw new InvalidRiotIdException();
            }
            throw new StatusCodeException(e.getMessage());
        }

    }

    public static RiotAccount getAccountByPuuid(String puuid) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public static PlatformData getPlatformData(String region) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://" + region + ".api.riotgames.com/val/status/v1/platform-data");
        return new Gson().fromJson(jsonObject, PlatformData.class);
    }

    public static ActiveShard getActiveShard(String puuid) throws StatusCodeException, InvalidRegionException {
        try {
            JsonObject jsonObject = performHttpRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
            return new Gson().fromJson(jsonObject, ActiveShard.class);
        } catch (StatusCodeException e) {
            String[] message = e.getMessage().split(" ");
            int statusCode = Integer.parseInt(message[0]);
            if(statusCode == 404) {
                throw new InvalidRegionException();
            }
            throw new StatusCodeException(e.getMessage());
        }
    }

    private static JsonObject performHttpRequest(String uri) throws StatusCodeException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("X-Riot-Token", ConfigManager.getTokensConfig().getApi().getRiot())
                .GET()
                .build();
        return new ApiHttpRequest().performHttpRequest(request);
    }

}
