package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
        final TextChannel logChannel = supportGuild.getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getGuild());
        Embed embed = new Embed()
                .setColor(0, 255, 0)
                .setAuthor(event.getGuild().getName() + " (" + event.getGuild().getId() + ")", event.getGuild().getIconUrl())
                .addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag() + " (" + event.getGuild().getOwner().getId() + ")", true)
                .addField("Member count", event.getGuild().getMemberCount() + " members", true)
                .setThumbnail(event.getGuild().getIconUrl());
        if (event.getGuild().getBannerUrl() != null) {
            embed.setImage(event.getGuild().getBannerUrl());
        }
        CompletableFuture.runAsync(() -> logChannel.sendMessageEmbeds(embed.build()).queue());
        if (!Bot.isDebug()) {
            StatsCounter.countGuilds();
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        final Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
        final TextChannel logChannel = supportGuild.getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getGuild());
        Embed embed = new Embed()
                .setColor(255, 0, 0)
                .setAuthor(event.getGuild().getName() + " (" + event.getGuild().getId() + ")", event.getGuild().getIconUrl())
                .addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag() + " (" + event.getGuild().getOwner().getId() + ")", true)
                .addField("Member count", event.getGuild().getMemberCount() + " members", true)
                .setThumbnail(event.getGuild().getIconUrl());
        if (event.getGuild().getBannerUrl() != null) {
            embed.setImage(event.getGuild().getBannerUrl());
        }
        CompletableFuture.runAsync(() -> logChannel.sendMessageEmbeds(embed.build()).queue());
        if (!Bot.isDebug()) {
            StatsCounter.countGuilds();
        }
    }



}
