package dev.piste.vayna.apis.riot.gson;

// GSON CLASS
@SuppressWarnings("ALL")
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

}
