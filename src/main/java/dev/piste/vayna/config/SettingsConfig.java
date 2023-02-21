package dev.piste.vayna.config;

// GSON CLASS
@SuppressWarnings("ALL")
public class SettingsConfig {

    private String version;
    private String websiteUri;
    private long supportGuildId;
    private String supportGuildInviteUri;
    private LogChannelIds logChannelIds;
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
        private long guild;
        private long feedback;
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
        private Channel guilds;
        private Channel connections;

        public Channel getGuilds() {
            return guilds;
        }

        public Channel getConnections() {
            return connections;
        }

        public static class Channel {
            private long id;
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
