package dev.piste.vayna.listener;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.config.Configs;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.messages.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        try {
            CommandManager.perform(event);
        } catch (StatusCodeException e) {
            Embed embed = ErrorMessages.getStatusCodeErrorMessage(event.getUser(), event.getGuild(), e);
            event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                    Button.link(Configs.getSettings().getSupportGuild().getInviteUri(), "Support").withEmoji(Emoji.getDiscord())
            ).queue();
            if(Bot.isDebug()) return;
            TextChannel logChannel = Bot.getJDA().getGuildById(Configs.getSettings().getSupportGuild().getId()).getTextChannelById(Configs.getSettings().getLogChannels().getError());
            embed.addField("URL", e.getMessage().split(" ")[1], false)
                            .setAuthor(event.getUser().getAsTag(), Configs.getSettings().getWebsiteUri(), event.getUser().getAvatarUrl())
                            .setDescription(" ");
            logChannel.sendMessageEmbeds(embed.build()).queue();
        }
    }

}
