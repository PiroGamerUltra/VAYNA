package dev.piste.vayna.counter;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.config.settings.SettingsConfig;
import dev.piste.vayna.mongodb.Mongo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class StatsCounter {

    private static long connectionsCounterRateLimitTimestampMillis = 0;
    private static long guildsCounterRateLimitTimestampMillis = 0;

    public static void countConnections() {
        if(Bot.isDebug()) return;
        if(connectionsCounterRateLimitTimestampMillis == 0 || connectionsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            SettingsConfig settingsConfig = Configs.getSettings();
            Guild supportGuild = Bot.getJDA().getGuildById(settingsConfig.getSupportGuild().getId());
            String connectionCountChannelName = settingsConfig.getBotStatsChannels().getConnectionChannelName().replace("%count%", Mongo.getLinkedAccountCollection().countDocuments() + "");
            VoiceChannel connectionCountChannel = supportGuild.getVoiceChannelById(settingsConfig.getBotStatsChannels().getConnectionChannelId());
            connectionCountChannel.getManager().setName(connectionCountChannelName).queue();
            connectionsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }
    }

    public static void countGuilds() {
        if(Bot.isDebug()) return;
        if(guildsCounterRateLimitTimestampMillis == 0 || guildsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            SettingsConfig settingsConfig = Configs.getSettings();
            Guild supportGuild = Bot.getJDA().getGuildById(settingsConfig.getSupportGuild().getId());
            String guildCountChannelName = settingsConfig.getBotStatsChannels().getGuildChannelName().replace("%count%", Bot.getJDA().getGuilds().size() + "");
            VoiceChannel guildCountChannel = supportGuild.getVoiceChannelById(settingsConfig.getBotStatsChannels().getGuildChannelId());
            guildCountChannel.getManager().setName(guildCountChannelName).queue();
            guildsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }

    }

}
