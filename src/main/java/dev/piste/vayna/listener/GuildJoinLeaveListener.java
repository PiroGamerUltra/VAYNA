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

/**
 * @author Piste | https://github.com/zPiste
 */
public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if(Bot.isDebug()) return;
        Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
        Embed embed = new Embed();
        embed.setColor(0, 255, 0);
        embed.setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getGuild());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        StatsCounter.countGuilds();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        if(Bot.isDebug()) return;
        Guild supportGuild = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId());
        Embed embed = new Embed();
        embed.setColor(255, 0, 0);
        embed.setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getGuild());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        StatsCounter.countGuilds();
    }



}
