package dev.piste.vayna.http.models.riotgames;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class Leaderboard {

    @SerializedName("actId")
    private String actId;
    @SerializedName("players")
    private List<Player> players;
    @SerializedName("totalPlayers")
    private int totalPlayerCount;
    @SerializedName("immortalStartingPage")
    private int immortalStartingPage;
    @SerializedName("immortalStartingIndex")
    private int immortalStartingIndex;
    @SerializedName("topTierRRThreshold")
    private int topRankRatingThreshold;
    @SerializedName("tierDetails")
    private TierDetails tierDetails;
    @SerializedName("startIndex")
    private int startIndex;
    @SerializedName("query")
    private String query;
    @SerializedName("shard")
    private String region;

    public String getActId() {
        return actId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalPlayerCount() {
        return totalPlayerCount;
    }

    public int getImmortalStartingPage() {
        return immortalStartingPage;
    }

    public int getImmortalStartingIndex() {
        return immortalStartingIndex;
    }

    public int getTopRankRatingThreshold() {
        return topRankRatingThreshold;
    }

    public TierDetails getTierDetails() {
        return tierDetails;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public String getQuery() {
        return query;
    }

    public String getRegion() {
        return region;
    }

    public static class Player {

        @SerializedName("puuid")
        private String puuid;
        @SerializedName("gameName")
        private String name;
        @SerializedName("tagLine")
        private String tag;
        @SerializedName("leaderboardRank")
        private int leaderboardRank;
        @SerializedName("rankedRating")
        private int rating;
        @SerializedName("numberOfWins")
        private int winCount;
        @SerializedName("competitiveTier")
        private int rankId;

        public String getPUUID() {
            return puuid;
        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return tag;
        }

        public int getLeaderboardRank() {
            return leaderboardRank;
        }

        public int getRating() {
            return rating;
        }

        public int getWinCount() {
            return winCount;
        }

        public int getRankId() {
            return rankId;
        }

    }

    public static class TierDetails {

        @SerializedName("24")
        private TierDetail immortal1;
        @SerializedName("25")
        private TierDetail immortal2;
        @SerializedName("26")
        private TierDetail immortal3;
        @SerializedName("27")
        private TierDetail radiant;

        public TierDetail getImmortal1() {
            return immortal1;
        }

        public TierDetail getImmortal2() {
            return immortal2;
        }

        public TierDetail getImmortal3() {
            return immortal3;
        }

        public TierDetail getRadiant() {
            return radiant;
        }

        public static class TierDetail {

            @SerializedName("rankedRatingThreshold")
            private int ratingThreshold;
            @SerializedName("startingPage")
            private int startingPage;
            @SerializedName("startingIndex")
            private int startingIndex;

            public int getRatingThreshold() {
                return ratingThreshold;
            }

            public int getStartingPage() {
                return startingPage;
            }

            public int getStartingIndex() {
                return startingIndex;
            }

        }

    }

}