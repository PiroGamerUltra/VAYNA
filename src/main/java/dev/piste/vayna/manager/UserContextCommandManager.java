package dev.piste.vayna.manager;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.commands.context.StatsContextCommand;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.util.HashMap;

/**
 * @author Piste | https://github.com/zPiste
 */
public class UserContextCommandManager {

    private static final HashMap<String, UserContextCommand> userContextCommands = new HashMap<>();

    public static void registerStringSelectMenus() {
        addUserContextCommand(new StatsContextCommand());
    }

    private static void addUserContextCommand(UserContextCommand userContextCommand) {
        userContextCommands.put(userContextCommand.getName(), userContextCommand);
        userContextCommand.register();
    }

    public static void perform(UserContextInteractionEvent event) {
        Thread thread = new Thread(() -> {
            try {
                userContextCommands.get(event.getName()).perform(event);
            } catch (StatusCodeException e) {
                Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                if(Bot.isDebug()) return;
                TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuild().getId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannels().getError());
                embed.addField("URL", e.getMessage().split(" ")[1], false)
                        .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                        .setDescription(" ");
                logChannel.sendMessageEmbeds(embed.build()).queue();
            }
        });
        thread.start();
    }

}
