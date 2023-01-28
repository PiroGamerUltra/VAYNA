package dev.piste.vayna.apis.riotgames.gson;

import dev.piste.vayna.apis.riotgames.RiotAPI;

// GSON CLASS
public class ActiveShard {

    private String activeShard;

    public String getActiveShard() {
        return activeShard;
    }

    public PlatformData getPlatformData() {
        return RiotAPI.getPlatformData(activeShard);
    }

}
