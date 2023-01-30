package dev.piste.vayna.apis.henrik;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.ApiHttpRequest;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMR;
import dev.piste.vayna.config.Configs;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;

public class HenrikAPI {

    public static HenrikAccount getAccountByRiotId(String gameName, String tagLine) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://api.henrikdev.xyz/valorant/v1/account/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?force=true");
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, HenrikAccount.class);
    }

    public static ArrayList<CurrentBundle> getCurrentBundles() throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://api.henrikdev.xyz/valorant/v2/store-featured");
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        return new Gson().fromJson(dataArray, new TypeToken<ArrayList<CurrentBundle>>(){}.getType());
    }

    public static MMR getMmr(String puuid, String region) throws StatusCodeException {
        JsonObject jsonObject = performHttpRequest("https://api.henrikdev.xyz/valorant/v2/by-puuid/mmr/" + region + "/" + puuid);
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        return new Gson().fromJson(dataObject, MMR.class);
    }

    private static JsonObject performHttpRequest(String uri) throws StatusCodeException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                //.header("Authorization", Configs.getTokens().getApi().getHenrik())
                .GET()
                .build();
        return new ApiHttpRequest().performHttpRequest(request);
    }

}
