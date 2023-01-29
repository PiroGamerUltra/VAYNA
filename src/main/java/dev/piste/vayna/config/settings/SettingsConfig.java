package dev.piste.vayna.config.settings;

// GSON CLASS
@SuppressWarnings("ALL")
public class SettingsConfig {

    private String version;
    private String websiteUri;
    private SupportGuild supportGuild;
    private LogChannels logChannels;
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

    public LogChannels getLogChannels() {
        return logChannels;
    }

    public BotStatsChannels getBotStatsChannels() {
        return botStatsChannels;
    }

}
