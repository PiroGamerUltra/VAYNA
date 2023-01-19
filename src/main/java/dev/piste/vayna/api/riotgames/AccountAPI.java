package dev.piste.vayna.api.riotgames;

import dev.piste.vayna.api.HttpRequest;
import org.json.simple.JSONObject;

public class AccountAPI {
    public static String[] getByPuuid(String puuid) {
        JSONObject account = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        String[] result = new String[2];
        result[0] = (String) account.get("gameName");
        result[1] = (String) account.get("tagLine");
        return result;
    }

    public static String getByRiotId(String gameName, String tagLine) {
        JSONObject account = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);
        return (String) account.get("puuid");
    }

    public static String getActiveShard(String puuid) {
        JSONObject account = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        return (String) account.get("activeShard");
    }

}
