package dev.piste.vayna.counter;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.mongodb.Mongo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class StatsCounter {

    private static long connectionsCounterRateLimitTimestampMillis = 0;
    private static long guildsCounterRateLimitTimestampMillis = 0;

    public static void countConnections() {
        if(Bot.isDebug()) return;
        if(connectionsCounterRateLimitTimestampMillis == 0 || connectionsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuild().getId());
            String connectionCountChannelName = ConfigManager.getSettingsConfig().getBotStatsChannels().getConnectionChannelName().replace("%count%", Mongo.getLinkedAccountCollection().countDocuments() + "");
            VoiceChannel connectionCountChannel = supportGuild.getVoiceChannelById(ConfigManager.getSettingsConfig().getBotStatsChannels().getConnectionChannelId());
            if(connectionCountChannel.getName().equals(connectionCountChannelName)) return;
            connectionCountChannel.getManager().setName(connectionCountChannelName).queue();
            connectionsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }
    }

    public static void countGuilds() {
        if(Bot.isDebug()) return;
        if(guildsCounterRateLimitTimestampMillis == 0 || guildsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuild().getId());
            String guildCountChannelName = ConfigManager.getSettingsConfig().getBotStatsChannels().getGuildChannelName().replace("%count%", Bot.getJDA().getGuilds().size() + "");
            VoiceChannel guildCountChannel = supportGuild.getVoiceChannelById(ConfigManager.getSettingsConfig().getBotStatsChannels().getGuildChannelId());
            if(guildCountChannel.getName().equals(guildCountChannelName)) return;
            guildCountChannel.getManager().setName(guildCountChannelName).queue();
            guildsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }

    }

}
