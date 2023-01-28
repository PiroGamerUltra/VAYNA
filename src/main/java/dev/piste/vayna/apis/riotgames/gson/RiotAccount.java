package dev.piste.vayna.apis.riotgames.gson;

import dev.piste.vayna.apis.henrik.HenrikAPI;
import dev.piste.vayna.apis.henrik.gson.HenrikAccount;
import dev.piste.vayna.apis.riotgames.RiotAPI;
import dev.piste.vayna.exceptions.HenrikAccountException;
import dev.piste.vayna.exceptions.RiotAccountException;

// GSON CLAS
public class RiotAccount {

    private String puuid;
    private String gameName;
    private String tagLine;

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

    public HenrikAccount getHenrikAccount() throws HenrikAccountException {
        return HenrikAPI.getAccountByRiotId(gameName, tagLine);
    }

    public ActiveShard getActiveShard() throws RiotAccountException {
        return RiotAPI.getActiveShard(puuid);
    }

}
