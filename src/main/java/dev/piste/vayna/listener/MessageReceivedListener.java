package dev.piste.vayna.listener;

import dev.piste.vayna.main.Bot;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isFromType(ChannelType.PRIVATE) && event.getAuthor() != Bot.getJDA().getSelfUser()) {
            User piste = Bot.getJDA().getUserById(571013043509657612l);
            piste.openPrivateChannel().queue((privateChannel -> {
                Embed embed = new Embed();
                embed.setDescription(event.getMessage().getContentRaw());
                embed.setAuthor(event.getAuthor().getAsTag(), Bot.getWebsiteUrl(), event.getAuthor().getAvatarUrl());
                privateChannel.sendMessageEmbeds(embed.build()).queue();
            }));
        }
    }

}
