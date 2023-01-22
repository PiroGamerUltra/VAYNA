package dev.piste.vayna.api.riotgames;

import org.json.simple.JSONObject;

public class Account {

    private final String gameName;
    private final String tagLine;
    private final String puuid;

    private final String activeShard;
    public Account(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
        JSONObject accountJson = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);
        this.puuid = (String) accountJson.get("puuid");
        JSONObject activeShardJson = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        this.activeShard = (String) activeShardJson.get("activeShard");
    }

    public Account(String puuid) {
        this.puuid = puuid;
        JSONObject accountJson = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        this.gameName = (String) accountJson.get("gameName");
        this.tagLine = (String) accountJson.get("tagLine");
        JSONObject activeShardJson = HttpRequest.doHttpRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        this.activeShard = (String) activeShardJson.get("activeShard");
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getPuuid() {
        return puuid;
    }

    public String getActiveShard() {
        return activeShard;
    }

    public String getRiotId() {
        return gameName + "#" + tagLine;
    }


}
