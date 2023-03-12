package dev.piste.vayna.http.models.riotgames;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class RiotAccount {

    @SerializedName("puuid")
    private String puuid;
    @SerializedName("gameName")
    private String name;
    @SerializedName("tagLine")
    private String tag;

    public String getPUUID() {
        return puuid;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getRiotId() {
        return name + "#" + tag;
    }

}