package dev.piste.vayna.config;

import com.google.gson.annotations.SerializedName;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public class SettingsConfig {

    @SerializedName("version")
    private String version;
    @SerializedName("websiteUri")
    private String websiteUri;
    @SerializedName("supportGuildId")
    private long supportGuildId;
    @SerializedName("supportGuildInviteUri")
    private String supportGuildInviteUri;
    @SerializedName("logChannelIds")
    private LogChannelIds logChannelIds;
    @SerializedName("statsChannels")
    private StatsChannel statsChannel;

    public String getVersion() {
        return version;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public long getSupportGuildId() {
        return supportGuildId;
    }

    public String getSupportGuildInviteUri() {
        return supportGuildInviteUri;
    }

    public LogChannelIds getLogChannelIds() {
        return logChannelIds;
    }

    public StatsChannel getStatsChannel() {
        return statsChannel;
    }

    public static class LogChannelIds {

        @SerializedName("guild")
        private long guild;
        @SerializedName("feedback")
        private long feedback;
        @SerializedName("error")
        private long error;

        public long getGuild() {
            return guild;
        }

        public long getFeedback() {
            return feedback;
        }

        public long getError() {
            return error;
        }

    }
    public static class StatsChannel {

        @SerializedName("guilds")
        private Channel guilds;
        @SerializedName("connections")
        private Channel connections;

        public Channel getGuilds() {
            return guilds;
        }

        public Channel getConnections() {
            return connections;
        }

        public static class Channel {

            @SerializedName("id")
            private long id;
            @SerializedName("name")
            private String name;

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

        }

    }

}
