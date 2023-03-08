package dev.piste.vayna.apis.entities.henrik;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class HenrikAccount {

    @SerializedName("puuid")
    private String id;
    @SerializedName("region")
    private String region;
    @SerializedName("account_level")
    private int level;
    @SerializedName("name")
    private String name;
    @SerializedName("tag")
    private String tag;
    @SerializedName("card")
    private PlayerCard playerCard;
    @SerializedName("last_update")
    private String lastUpdatePatched;
    @SerializedName("last_update_raw")
    private long lastUpdateSeconds;

    public String getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public int getLevel() {
        return level;
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

    public PlayerCard getPlayerCard() {
        return playerCard;
    }

    public String getLastUpdatePatched() {
        return lastUpdatePatched;
    }

    public Date getLastUpdate() {
        return new Date(lastUpdateSeconds * 1000);
    }

    public static class PlayerCard {

        @SerializedName("id")
        private String id;
        @SerializedName("small")
        private String smallArt;
        @SerializedName("wide")
        private String wideArt;
        @SerializedName("large")
        private String largeArt;

        public String getId() {
            return id;
        }

        public String getSmallArt() {
            return smallArt;
        }

        public String getWideArt() {
            return wideArt;
        }

        public String getLargeArt() {
            return largeArt;
        }

    }

}