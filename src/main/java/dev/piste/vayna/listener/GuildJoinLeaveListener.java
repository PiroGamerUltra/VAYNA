package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Guild supportGuild = Bot.getJDA().getGuildById(SettingsConfig.getSupportGuildId());
        Embed embed = new Embed();
        embed.setColor(0, 255, 0);
        embed.setAuthor(event.getGuild().getName(), SettingsConfig.getWebsiteUri(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(SettingsConfig.getGuildJoinActivitiesChannelId());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        countGuilds(supportGuild);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild supportGuild = Bot.getJDA().getGuildById(SettingsConfig.getSupportGuildId());
        Embed embed = new Embed();
        embed.setColor(255, 0, 0);
        embed.setAuthor(event.getGuild().getName(), SettingsConfig.getWebsiteUri(), event.getGuild().getIconUrl());
        if(event.getGuild().getBannerUrl() != null) embed.setImage(event.getGuild().getBannerUrl());
        embed.addField("Guild owner", event.getGuild().getOwner().getUser().getAsTag(), true);
        embed.addField("Member count", event.getGuild().getMemberCount() + " members", true);
        embed.setThumbnail(event.getGuild().getIconUrl());
        TextChannel textChannel = supportGuild.getTextChannelById(SettingsConfig.getGuildLeaveActivitiesChannelId());
        textChannel.sendMessageEmbeds(embed.build()).queue();

        countGuilds(supportGuild);
    }

    private void countGuilds(Guild supportGuild) {
        if(Bot.isDebug()) return;
        String guildCountChannelName = SettingsConfig.getGuildCountChannelName().replace("%count%", Bot.getJDA().getGuilds().size() + "");
        VoiceChannel guildCountChannel = supportGuild.getVoiceChannelById(SettingsConfig.getGuildCountChannelId());
        guildCountChannel.getManager().setName(guildCountChannelName).queue();
    }

}
