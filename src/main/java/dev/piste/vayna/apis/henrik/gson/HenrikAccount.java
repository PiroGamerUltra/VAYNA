package dev.piste.vayna.apis.henrik.gson;

import dev.piste.vayna.apis.henrik.gson.account.Card;

// GSON CLASS
public class HenrikAccount {

    private String puuid;
    private int account_level;
    private Card card;

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
