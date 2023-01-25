package dev.piste.vayna.api.riotgames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.HenrikAccount;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.RiotAccountException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RiotAccount {

    private String puuid;
    private String gameName;
    private String tagLine;

    public static RiotAccount getByRiotId(String gameName, String tagLine) throws RiotAccountException {
        JsonNode accountNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8));
        if(accountNode.get("puuid") == null) throw new RiotAccountException();
        try {
            RiotAccount riotAccount = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(accountNode), new TypeReference<RiotAccount>() {});
            return riotAccount;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static RiotAccount getByPuuid(String puuid) {
        JsonNode accountNode = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid);
        try {
            RiotAccount riotAccount = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(accountNode), new TypeReference<RiotAccount>() {});
            return riotAccount;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
