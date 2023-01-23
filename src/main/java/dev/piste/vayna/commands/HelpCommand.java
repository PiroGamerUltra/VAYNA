package dev.piste.vayna.commands;

import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        Embed embedBuilder = new Embed();
        embedBuilder.setTitle("» Help");
        embedBuilder.addField("Version", "The current version of the bot is `v" + SettingsConfig.getVersion() + "`", true);
        embedBuilder.addField("General Commands",  "» " + CommandManager.findCommand("help").getAsMention() + " Retrieve general information about the bot and a list of all available commands", false);
        embedBuilder.addField("Connection Commands",  "» " + CommandManager.findCommand("connection").getAsMention() + " Manage the connection to your Riot-Games account", false);
        embedBuilder.addField("Statistic Commands", "» " + CommandManager.findSubcommand(CommandManager.findCommand("stats"), "user").getAsMention() + " Retrieve general statistics about a provided Discord user or yourself\n" +
                "» " + CommandManager.findSubcommand(CommandManager.findCommand("stats"), "riotid").getAsMention() + " Retrieve general statistics about a provided Riot-ID", false);

        event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Button.link(SettingsConfig.getSupportGuildInviteLink(), "Support"),
                Button.link(SettingsConfig.getWebsiteUri() + "/redirect/github", "GitHub"),
                Button.link(SettingsConfig.getWebsiteUri() + "/redirect/topgg", "Top.GG")
        ).queue();

    }

}
