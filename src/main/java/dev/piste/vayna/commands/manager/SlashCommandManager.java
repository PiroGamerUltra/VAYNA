package dev.piste.vayna.commands.manager;

import dev.piste.vayna.Bot;
import dev.piste.vayna.apis.StatusCodeException;
import dev.piste.vayna.commands.slash.*;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.templates.Buttons;
import dev.piste.vayna.util.templates.ErrorMessages;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piste | https://github.com/zPiste
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
        } catch (StatusCodeException e) {
            if(Bot.isDebug()) {
                System.out.println("Error registering slashCommand: " + e.getMessage());
                return;
            }
            TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
            Embed embed = new Embed()
                    .setTitle("Register slashCommand HTTP error")
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
            } catch (StatusCodeException e) {
                Embed embed = ErrorMessages.getStatusCodeErrorEmbed(event.getGuild(), event.getUser(), e);
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                        Buttons.getSupportButton(event.getGuild())
                ).queue();
                if(Bot.isDebug()) return;
                TextChannel logChannel = Bot.getJDA().getGuildById(ConfigManager.getSettingsConfig().getSupportGuildId()).getTextChannelById(ConfigManager.getSettingsConfig().getLogChannelIds().getError());
                embed.addField("URL", e.getMessage().split(" ")[1], false)
                        .setAuthor(event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                        .setDescription(" ");
                logChannel.sendMessageEmbeds(embed.build()).queue();
            }
        });
        thread.start();
    }

    public static net.dv8tion.jda.api.interactions.commands.Command getAsJdaCommand(SlashCommand slashCommand) {
        for(net.dv8tion.jda.api.interactions.commands.Command jdaCommand : Bot.getJDA().retrieveCommands().complete()) {
            if(jdaCommand.getName().equalsIgnoreCase(slashCommand.getName())) return jdaCommand;
        }
        return null;
    }


}
