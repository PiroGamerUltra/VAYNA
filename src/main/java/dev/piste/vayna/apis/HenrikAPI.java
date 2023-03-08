package dev.piste.vayna.apis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.piste.vayna.apis.entities.henrik.HenrikAccount;
import dev.piste.vayna.apis.entities.henrik.MMR;
import dev.piste.vayna.apis.entities.henrik.StoreBundle;
import dev.piste.vayna.config.ConfigManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class HenrikAPI {

    private final String BASE_URL = "https://api.henrikdev.xyz/valorant";
    private final RestClient restClient = new RestClient(BASE_URL).appendHeader("Authorization", ConfigManager.getTokensConfig().getApiKeys().getHenrik());

    public HenrikAccount getAccount(String name, String tag) throws HttpErrorException, IOException, InterruptedException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String encodedTag = URLEncoder.encode(tag, StandardCharsets.UTF_8);
        JsonObject jsonObject = restClient.doGet(String.format("/v1/account/%s/%s?force=true", encodedName, encodedTag)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, HenrikAccount.class);
    }

    public MMR getMMR(String id, String region) throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet(String.format("/v2/by-puuid/mmr/%s/%s", region, id)).getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, MMR.class);
    }

    public List<StoreBundle> getStoreBundles() throws IOException, HttpErrorException, InterruptedException {
        JsonObject jsonObject = restClient.doGet("/v2/store-featured").getAsJsonObject("data");
        return new Gson().fromJson(jsonObject, new TypeToken<List<StoreBundle>>(){}.getType());
    }

}
