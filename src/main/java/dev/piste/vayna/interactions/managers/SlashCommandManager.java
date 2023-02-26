package dev.piste.vayna.interactions.managers;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.interactions.commands.slash.*;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class SlashCommandManager {

    private static final Map<String, SlashCommand> slashCommands = new HashMap<>();

    public static void registerCommands() {
        addCommand(new AgentSlashCommand());
        addCommand(new ConnectionSlashCommand());
        addCommand(new GamemodeSlashCommand());
        addCommand(new HelpSlashCommand());
        addCommand(new MapSlashCommand());
        addCommand(new StatsSlashCommand());
        addCommand(new WeaponSlashCommand());
        addCommand(new StoreSlashCommand());
        addCommand(new LeaderboardSlashCommand());
        addCommand(new FeedbackSlashCommand());
        addCommand(new SettingsSlashCommand());

        for(net.dv8tion.jda.api.interactions.commands.Command command : Bot.getJDA().retrieveCommands().complete()) {
            if(!slashCommands.containsKey(command.getName().toLowerCase())) {
                command.delete().queue();
            }
        }
    }

    private static void addCommand(SlashCommand slashCommand) {
        slashCommands.put(slashCommand.getName(), slashCommand);
        try {
            Bot.getJDA().upsertCommand(slashCommand.getCommandData()).queue();
        } catch (HttpErrorException e) {
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            Embed embed = new Embed()
                    .setTitle("Register command HTTP error")
                    .setDescription(e.getMessage());
            logChannel.sendMessageEmbeds(embed.build()).queue();
        }
    }

    public static Collection<SlashCommand> getCommands() {
        return slashCommands.values();
    }

    public static void perform(SlashCommandInteractionEvent event) {

        Thread thread = new Thread(() -> {
            try {
                slashCommands.get(event.getName().toLowerCase()).perform(event);
            } catch (HttpErrorException e) {
                Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
                embed.addField("URL", e.getUri(), false)
                        .addField("Request Method", e.getRequestMethod(), false)
                        .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                        .setDescription(" ");
                logChannel.sendMessageEmbeds(embed.build()).queue();
            }
        });
        thread.start();
    }

    public static Command getAsJdaCommand(SlashCommand slashCommand) {
        for(Command jdaCommand : Bot.getJDA().retrieveCommands().complete()) {
            if(jdaCommand.getName().equalsIgnoreCase(slashCommand.getName())) return jdaCommand;
        }
        return null;
    }


}
