package dev.piste.vayna.api;

import dev.piste.vayna.api.HttpRequest;
import org.json.simple.JSONObject;

public class Account {

    private final String gameName;
    private final String tagLine;
    private final String riotPuuid;
    private final String henrikPuuid;
    private final String activeShard;
    private final String regionName;
    private final String playerCardSmall;
    private final String playerCardLarge;
    private final String playerCardWide;
    private final long level;

    public Account(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
        JSONObject riotAccountJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);
        riotPuuid = (String) riotAccountJson.get("puuid");
        JSONObject activeShardJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + riotPuuid);
        activeShard = (String) activeShardJson.get("activeShard");
        JSONObject regionNameJson = HttpRequest.doRiotApiRequest("https://" + activeShard + ".api.riotgames.com/val/status/v1/platform-data");
        regionName = (String) regionNameJson.get("name");

        JSONObject henrikAccountJson = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + gameName + "/" + tagLine + "?force=true");
        JSONObject dataObject = (JSONObject) henrikAccountJson.get("data");
        henrikPuuid = (String) dataObject.get("puuid");
        JSONObject cardObject = (JSONObject) dataObject.get("card");
        playerCardSmall = (String) cardObject.get("playerCardSmall");
        playerCardLarge = (String) cardObject.get("playerCardLarge");
        playerCardWide = (String) cardObject.get("playerCardWide");
        level = (long) dataObject.get("account_level");
    }

    public Account(String puuid) {
        this.riotPuuid = puuid;
        JSONObject accountJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + riotPuuid);
        gameName = (String) accountJson.get("gameName");
        tagLine = (String) accountJson.get("tagLine");
        JSONObject activeShardJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + riotPuuid);
        activeShard = (String) activeShardJson.get("activeShard");
        JSONObject regionNameJson = HttpRequest.doRiotApiRequest("https://" + activeShard + ".api.riotgames.com/val/status/v1/platform-data");
        regionName = (String) regionNameJson.get("name");

        JSONObject henrikAccountJson = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + gameName + "/" + tagLine + "?force=true");
        JSONObject dataObject = (JSONObject) henrikAccountJson.get("data");
        henrikPuuid = (String) dataObject.get("puuid");
        JSONObject cardObject = (JSONObject) dataObject.get("card");
        playerCardSmall = (String) cardObject.get("small");
        playerCardLarge = (String) cardObject.get("large");
        playerCardWide = (String) cardObject.get("wide");
        level = (long) dataObject.get("account_level");

    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public String getHenrikPuuid() {
        return henrikPuuid;
    }

    public String getActiveShard() {
        return activeShard;
    }

    public String getRegionName() {
        return regionName;
    }

    public long getLevel() {
        return level;
    }

    public String getPlayerCardSmall() {
        return playerCardSmall;
    }

    public String getPlayerCardLarge() {
        return playerCardLarge;
    }

    public String getPlayerCardWide() {
        return playerCardWide;
    }

    public String getRiotId() {
        return gameName + "#" + tagLine;
    }


}
