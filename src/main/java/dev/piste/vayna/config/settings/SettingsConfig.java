package dev.piste.vayna.config.settings;

public class SettingsConfig {

    private String version;
    private String websiteUri;
    private SupportGuild supportGuild;
    private GuildActivitiesChannels guildActivitiesChannels;
    private BotStatsChannels botStatsChannels;

    public String getVersion() {
        return version;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public SupportGuild getSupportGuild() {
        return supportGuild;
    }

    public GuildActivitiesChannels getGuildActivitiesChannels() {
        return guildActivitiesChannels;
    }

    public BotStatsChannels getBotStatsChannels() {
        return botStatsChannels;
    }

}
