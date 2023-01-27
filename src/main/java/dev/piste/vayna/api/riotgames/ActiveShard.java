package dev.piste.vayna.api.riotgames;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.piste.vayna.api.HttpRequest;
import dev.piste.vayna.exceptions.RiotAccountException;

public class ActiveShard {

    private String activeShard;

    public static ActiveShard getByPuuid(String puuid) throws RiotAccountException {
        JsonObject jsonObject = HttpRequest.doRiotApiRequest("https://europe.api.riotgames.com/riot/account/v1/active-shards/by-game/val/by-puuid/" + puuid);
        if(jsonObject.get("activeShard") == null) throw new RiotAccountException();
        ActiveShard activeShard = new Gson().fromJson(jsonObject, ActiveShard.class);
        return activeShard;
    }

    public String getActiveShard() {
        return activeShard;
    }

    public PlatformData getPlatformData() {
        return PlatformData.getByRegion(activeShard);
    }

}
