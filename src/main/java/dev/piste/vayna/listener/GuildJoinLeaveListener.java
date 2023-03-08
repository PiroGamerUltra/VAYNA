package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.StatsCounter;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
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
        log(event, event.getGuild());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        log(event, event.getGuild());
    }

    private void log(GenericEvent event, Guild guild) {
        Embed embed = new Embed()
                .setAuthor(guild.getName() + " (" + guild.getId() + ")", guild.getIconUrl())
                .addField("Guild owner", guild.getOwner().getUser().getAsTag() + " (" + guild.getOwner().getId() + ")", true)
                .addField("Member count", guild.getMemberCount() + " members", true)
                .setThumbnail(guild.getIconUrl());
        if(event instanceof GuildLeaveEvent) {
            embed.setColor(255, 0, 0);
        } else {
            embed.setColor(0, 255, 0);
        }
        if (guild.getBannerUrl() != null) {
            embed.setImage(guild.getBannerUrl());
        }
        Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
        TextChannel logChannel = supportGuild.getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getGuild());
        CompletableFuture.runAsync(() -> logChannel.sendMessageEmbeds(embed.build()).queue());
        if (!Bot.isDebug()) {
            StatsCounter.countGuilds();
        }
    }



}
