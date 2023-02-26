package dev.piste.vayna.apis.henrik;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.RestClient;
import dev.piste.vayna.apis.henrik.gson.CurrentBundle;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.henrik.gson.MMR;
import dev.piste.vayna.config.ConfigManager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HenrikAPI {

    private final String BASE_URL = "https://api.henrikdev.xyz/valorant";
    private final RestClient restClient = new RestClient(BASE_URL).appendHeader("Authorization", ConfigManager.getTokensConfig().getApiKeys().getHenrik());

    public HenrikAccount getAccount(String gameName, String tagLine) throws HttpErrorException {
        String encodedGameName = URLEncoder.encode(gameName, StandardCharsets.UTF_8);
        String encodedTagLine = URLEncoder.encode(tagLine, StandardCharsets.UTF_8);
        JsonObject jsonObject = restClient.doGet(String.format("/v1/account/%s/%s?force=true", encodedGameName, encodedTagLine)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, HenrikAccount.class);
    }

    public ArrayList<CurrentBundle> getCurrentBundles() throws HttpErrorException {
        JsonArray jsonArray = restClient.doGet("/v2/store-featured").getAsJsonArray("data");
        return new Gson().fromJson(jsonArray, new TypeToken<ArrayList<CurrentBundle>>(){}.getType());
    }

    public MMR getMmr(String puuid, String region) throws HttpErrorException {
        JsonObject jsonObject = restClient.doGet(String.format("/v2/by-puuid/mmr/%s/%s", region, puuid)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, MMR.class);
    }

}
