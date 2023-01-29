package dev.piste.vayna.config.settings;

// GSON CLASS
@SuppressWarnings("ALL")
public class BotStatsChannels {

    private long guildChannelId;
    private String guildChannelName;
    private long connectionChannelId;
    private String connectionChannelName;

    public long getGuildChannelId() {
        return guildChannelId;
    }

    public String getGuildChannelName() {
        return guildChannelName;
    }

    public long getConnectionChannelId() {
        return connectionChannelId;
    }

    public String getConnectionChannelName() {
        return connectionChannelName;
    }

}
