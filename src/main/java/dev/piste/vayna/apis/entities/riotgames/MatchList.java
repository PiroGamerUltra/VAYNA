package dev.piste.vayna.apis.entities.riotgames;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class MatchList {

    @SerializedName("history")
    private List<Entry> history;

    public List<Entry> getHistory() {
        return history;
    }

    public static class Entry {
        @SerializedName("matchId")
        private String matchId;
        @SerializedName("gameStartTimeMillis")
        private long gameStartTimeMillis;
        @SerializedName("queueId")
        private String queue;

        public String getMatchId() {
            return matchId;
        }

        public Date getGameStartDate() {
            return new Date(gameStartTimeMillis);
        }

        public String getQueue() {
            return queue;
        }

    }

}