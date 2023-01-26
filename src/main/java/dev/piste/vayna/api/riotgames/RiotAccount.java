package dev.piste.vayna.api.riotgames;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.HenrikAccount;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.RiotAccountException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RiotAccount {

    private String puuid;
    private String gameName;
    private String tagLine;

    public static RiotAccount getByRiotId(String gameName, String tagLine) throws RiotAccountException {
        JsonObject jsonObject = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
        if(jsonObject.get("puuid") == null) throw new RiotAccountException();
        RiotAccount riotAccount = new Gson().fromJson(jsonObject, RiotAccount.class);
        return riotAccount;
    }

    public static RiotAccount getByPuuid(String puuid) {
        JsonObject jsonObject = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        RiotAccount riotAccount = new Gson().fromJson(jsonObject, RiotAccount.class);
        return riotAccount;
    }

    public String getPuuid() {
        return puuid;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getRiotId() {
        return gameName + "#" + tagLine;
    }

    public ActiveShard getActiveShard() {
        return ActiveShard.getByPuuid(puuid);
    }

    public HenrikAccount getHenrikAccount() throws HenrikAccountException {
        return HenrikAccount.getByRiotId(gameName, tagLine);
    }


}
