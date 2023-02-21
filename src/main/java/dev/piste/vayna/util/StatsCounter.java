package dev.piste.vayna.util;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.mongodb.Mongo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.discordbots.api.client.DiscordBotListAPI;

public class StatsCounter {

    private static long connectionsCounterRateLimitTimestampMillis = 0;
    private static long guildsCounterRateLimitTimestampMillis = 0;

    public static void countConnections() {
        if(Bot.isDebug()) return;
        if(connectionsCounterRateLimitTimestampMillis == 0 || connectionsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
            String connectionCountChannelName = ConfigManager.getSettingsConfig().getStatsChannel().getConnections().getName().replace("%count%", Mongo.getLinkedAccountCollection().countDocuments() + "");
            VoiceChannel connectionCountChannel = supportGuild.getVoiceChannelById(ConfigManager.getSettingsConfig().getStatsChannel().getConnections().getId());
            if(connectionCountChannel.getName().equals(connectionCountChannelName)) return;
            connectionCountChannel.getManager().setName(connectionCountChannelName).queue();
            connectionsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }
    }

    public static void countGuilds() {
        if(Bot.isDebug()) return;
        new DiscordBotListAPI.Builder()
                .botId(Bot.getJDA().getSelfUser().getId())
                .token(ConfigManager.getTokensConfig().getApiKeys().getTopGG())
                .build()
                .setStats(Bot.getJDA().getGuilds().size());
        if(guildsCounterRateLimitTimestampMillis == 0 || guildsCounterRateLimitTimestampMillis<=System.currentTimeMillis()) {
            Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
            String guildCountChannelName = ConfigManager.getSettingsConfig().getStatsChannel().getGuilds().getName().replace("%count%", Bot.getJDA().getGuilds().size() + "");
            VoiceChannel guildCountChannel = supportGuild.getVoiceChannelById(ConfigManager.getSettingsConfig().getStatsChannel().getGuilds().getId());
            if(guildCountChannel.getName().equals(guildCountChannelName)) return;
            guildCountChannel.getManager().setName(guildCountChannelName).queue();
            guildsCounterRateLimitTimestampMillis = System.currentTimeMillis()+520000;
        }

    }

}
