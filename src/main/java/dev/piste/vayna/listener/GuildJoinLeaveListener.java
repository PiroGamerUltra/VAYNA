package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.counter.StatsCounter;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        if(Bot.isDebug()) return;
        Guild supportGuild = Bot.getJDA().getGuildById(Configs.getSettings().getSupportGuild().getId());
        Embed embed = new Embed();
        embed.setColor(0, 255, 0);
        embed.setAuthor(event.getGuild().getName(), Configs.getSettings().getWebsiteUri(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(Configs.getSettings().getGuildActivitiesChannels().getJoin());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        StatsCounter.countGuilds();
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if(Bot.isDebug()) return;
        Guild supportGuild = Bot.getJDA().getGuildById(Configs.getSettings().getSupportGuild().getId());
        Embed embed = new Embed();
        embed.setColor(255, 0, 0);
        embed.setAuthor(event.getGuild().getName(), Configs.getSettings().getWebsiteUri(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(Configs.getSettings().getGuildActivitiesChannels().getLeave());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        StatsCounter.countGuilds();
    }



}
