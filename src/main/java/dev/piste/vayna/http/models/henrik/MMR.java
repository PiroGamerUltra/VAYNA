package dev.piste.vayna.http.models.henrik;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class MMR {

    @SerializedName("name")
    private String name;
    @SerializedName("tag")
    private String tag;
    @SerializedName("current_data")
    private CurrentData currentData;
    @SerializedName("highest_rank")
    private PeakRank peakRank;

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public CurrentData getCurrentData() {
        return currentData;
    }

    public PeakRank getPeakRank() {
        return peakRank;
    }

    public static class CurrentData {

        @SerializedName("currenttier")
        private int rankId;
        @SerializedName("currenttierpatched")
        private String rankName;
        @SerializedName("images")
        private RankImages rankImages;
        @SerializedName("ranking_in_tier")
        private int rating;
        @SerializedName("mmr_change_to_last_game")
        private int lastRatingChange;
        @SerializedName("elo")
        private int elo;
        @SerializedName("games_needed_for_rating")
        private int neededGameCount;
        @SerializedName("old")
        private boolean isOld;

        public int getRankId() {
            return rankId;
        }

        public String getRankName() {
            return rankName;
        }

        public RankImages getRankImages() {
            return rankImages;
        }

        public int getRating() {
            return rating;
        }

        public int getLastRatingChange() {
            return lastRatingChange;
        }

        public int getNeededGameCount() {
            return neededGameCount;
        }

        public int getElo() {
            return elo;
        }

        public static class RankImages {

            @SerializedName("small")
            private String smallArt;
            @SerializedName("large")
            private String largeArt;
            @SerializedName("triangle_down")
            private String triangleDown;
            @SerializedName("triangle_up")
            private String triangleUp;

            public String getSmallArt() {
                return smallArt;
            }

            public String getLargeArt() {
                return largeArt;
            }

            public String getTriangleDown() {
                return triangleDown;
            }

            public String getTriangleUp() {
                return triangleUp;
            }

        }

    }

    public static class PeakRank {

        @SerializedName("old")
        private boolean isOld;
        @SerializedName("tier")
        private int rankId;
        @SerializedName("patched_tier")
        private String rankName;
        @SerializedName("season")
        private String seasonShort;

        public boolean isOld() {
            return isOld;
        }

        public int getRankId() {
            return rankId;
        }

        public String getRankName() {
            return rankName;
        }

        public String getSeasonShort() {
            return seasonShort;
        }
    }

}