package dev.piste.vayna.api.henrik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.piste.vayna.Bot;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.account.Card;
import dev.piste.vayna.exceptions.HenrikAccountException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HenrikAccount {

    private String puuid;
    private int accountLevel;
    private Card card;

    public static HenrikAccount getByRiotId(String gameName, String tagLine) throws HenrikAccountException {
        JsonNode accountNode = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?force=true");
        if(accountNode.get("data") == null) throw new HenrikAccountException();
        JsonNode dataNode = accountNode.get("data");
        try {
            HenrikAccount henrikAccount = Bot.getObjectMapper().readValue(Bot.getObjectMapper().writeValueAsString(dataNode), new TypeReference<HenrikAccount>() {});
            return henrikAccount;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPuuid() {
        return puuid;
    }

    public int getAccountLevel() {
        return accountLevel;
    }

    public Card getCard() {
        return card;
    }
}
