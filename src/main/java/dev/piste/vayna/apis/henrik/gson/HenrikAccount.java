package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.account.Card;

import java.util.List;

// GSON CLASS
@SuppressWarnings("ALL")
public class HenrikAccount {

    private String puuid;
    private String region;
    private int account_level;
    private Card card;

    public String getPuuid() {
        return puuid;
    }

    public String getRegion() {
        return region;
    }

    public int getAccountLevel() {
        return account_level;
    }

    public Card getCard() {
        return card;
    }

    public List<MMRHistory> getMmrHistory() {
        return HenrikAPI.getMmrHistory(puuid, region);
    }
}
