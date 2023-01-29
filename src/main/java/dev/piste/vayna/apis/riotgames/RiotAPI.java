package dev.piste.vayna.apis.riotgames;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.apis.HttpRequest;
import dev.piste.vayna.apis.riotgames.gson.ActiveShard;
import dev.piste.vayna.apis.riotgames.gson.PlatformData;
import dev.piste.vayna.apis.riotgames.gson.RiotAccount;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RiotAPI {

    public static RiotAccount getAccountByRiotId(String gameName, String tagLine) throws RiotApiException {
        JsonObject jsonObject = new HttpRequest().doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
        if(jsonObject.get("puuid") == null) throw new RiotApiException();
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public static RiotAccount getAccountByPuuid(String puuid) {
        JsonObject jsonObject = new HttpRequest().doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        return new Gson().fromJson(jsonObject, RiotAccount.class);
    }

    public static PlatformData getPlatformData(String region) {
        JsonObject jsonObject = new HttpRequest().doRiotApiRequest("https://" + region + ".api.riotgames.com/val/status/v1/platform-data");
        return new Gson().fromJson(jsonObject, PlatformData.class);
    }

    public static ActiveShard getActiveShard(String puuid) throws RiotApiException {
        JsonObject jsonObject = new HttpRequest().doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        if(jsonObject.get("activeShard") == null) throw new RiotApiException();
        return new Gson().fromJson(jsonObject, ActiveShard.class);
    }

}
