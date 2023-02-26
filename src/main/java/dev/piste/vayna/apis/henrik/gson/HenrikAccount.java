package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.account.Card;

/**
 * @author Piste | https://github.com/PisteDev
 */
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
}
