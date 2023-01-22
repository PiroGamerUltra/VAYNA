package dev.piste.vayna.api;

import dev.piste.vayna.api.HttpRequest;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Account {

    private final String gameName;
    private final String tagLine;
    private final String riotPuuid;
    private String activeShard;
    private String regionName;

    private String henrikPuuid;
    private String playerCardSmall;
    private String playerCardLarge;
    private String playerCardWide;
    private long level;
    private boolean henrikRequestExecuted;

    public Account(String gameName, String tagLine) throws UnknownRiotIdException {
        JSONObject riotAccountJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
        riotPuuid = (String) riotAccountJson.get("puuid");
        JSONObject accountJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + riotPuuid);
        this.gameName = (String) accountJson.get("gameName");
        this.tagLine = (String) accountJson.get("tagLine");
        if(riotPuuid == null) {
            throw new UnknownRiotIdException();
        }
        henrikRequestExecuted = false;
    }

    public Account(String puuid) {
        this.riotPuuid = puuid;
        JSONObject accountJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + riotPuuid);
        gameName = (String) accountJson.get("gameName");
        tagLine = (String) accountJson.get("tagLine");

        henrikRequestExecuted = false;
    }

    private void doHenrikRequest() {
        if(!henrikRequestExecuted) {
            JSONObject henrikAccountJson = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?force=true");
            JSONObject dataObject = (JSONObject) henrikAccountJson.get("data");

            henrikPuuid = (String) dataObject.get("puuid");
            JSONObject cardObject = (JSONObject) dataObject.get("card");
            playerCardSmall = (String) cardObject.get("small");
            playerCardLarge = (String) cardObject.get("large");
            playerCardWide = (String) cardObject.get("wide");
            level = (long) dataObject.get("account_level");
            henrikRequestExecuted = true;
        }
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

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public String getActiveShard() {
        JSONObject activeShardJson = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + riotPuuid);
        activeShard = (String) activeShardJson.get("activeShard");
        return activeShard;
    }

    public String getRegionName() {
        JSONObject regionNameJson = HttpRequest.doRiotApiRequest("https://" + getActiveShard() + ".api.riotgames.com/val/status/v1/platform-data");
        regionName = (String) regionNameJson.get("name");
        return regionName;
    }

    public long getLevel() {
        doHenrikRequest();
        return level;
    }

    public String getHenrikPuuid() {
        doHenrikRequest();
        return henrikPuuid;
    }
    public String getPlayerCardSmall() {
        doHenrikRequest();
        return playerCardSmall;
    }

    public String getPlayerCardLarge() {
        doHenrikRequest();
        return playerCardLarge;
    }

    public String getPlayerCardWide() {
        doHenrikRequest();
        return playerCardWide;
    }




}
