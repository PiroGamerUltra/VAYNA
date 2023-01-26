package dev.piste.vayna.api.henrik;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.api.henrik.account.Card;
import dev.piste.vayna.exceptions.HenrikAccountException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HenrikAccount {

    private String puuid;
    private int account_level;
    private Card card;

    public static HenrikAccount getByRiotId(String gameName, String tagLine) throws HenrikAccountException {
        JsonObject jsonObject = HttpRequest.doHenrikApiRequest("https://api.henrikdev.xyz/valorant/v1/account/" + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?force=true");
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        if(dataObject == null) throw new HenrikAccountException();
        return new Gson().fromJson(dataObject, HenrikAccount.class);
    }

    public String getPuuid() {
        return puuid;
    }

    public int getAccountLevel() {
        return account_level;
    }

    public Card getCard() {
        return card;
    }
}
