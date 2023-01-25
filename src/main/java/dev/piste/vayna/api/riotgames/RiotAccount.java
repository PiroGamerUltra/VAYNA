package dev.piste.vayna.api.riotgames;

import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.HenrikAccount;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.RiotAccountException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RiotAccount {

    private final String gameName;
    private final String tagLine;
    private final String puuid;
    private String activeShard;
    private String regionName;
    private HenrikAccount henrikAccount;

    public RiotAccount(String gameName, String tagLine) throws RiotAccountException {
        JsonNode accountNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
        if(accountNode.get("puuid") == null) throw new RiotAccountException();
        puuid = accountNode.get("puuid").asText();

        JsonNode puuidAccountNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        this.gameName = puuidAccountNode.get("gameName").asText();
        this.tagLine = puuidAccountNode.get("tagLine").asText();
    }

    public RiotAccount(String puuid) {
        JsonNode accountNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        gameName = accountNode.get("gameName").asText();
        tagLine = accountNode.get("tagLine").asText();
        this.puuid = accountNode.get("puuid").asText();
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

    public String getPuuid() {
        return puuid;
    }

    public String getActiveShard() {
        if(activeShard == null) {
            JsonNode jsonNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
            activeShard = jsonNode.get("activeShard").asText();
        }
        return activeShard;
    }

    public String getRegionName() {
        if(regionName == null) {
            JsonNode jsonNode = HttpRequest.doRiotApiRequest("https://" + getActiveShard() + ".api.riotgames.com/val/status/v1/platform-data");
            regionName = jsonNode.get("name").asText();
        }
        return regionName;
    }

    public HenrikAccount getHenrikAccount() throws HenrikAccountException {
        if(henrikAccount == null) henrikAccount = new HenrikAccount(gameName, tagLine);
        return henrikAccount;
    }
}
