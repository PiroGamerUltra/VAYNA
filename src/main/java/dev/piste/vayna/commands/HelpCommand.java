package dev.piste.vayna.commands;

import dev.piste.vayna.config.SettingsConfig;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    public static void performCommand(SlashCommandInteractionEvent event) {

        Embed embedBuilder = new Embed();
        embedBuilder.setTitle("» Help");
        embedBuilder.addField("Version", "The current version of the bot is `v" + SettingsConfig.getVersion() + "`", true);
        // General
        embedBuilder.addField("General Commands",  "" +
                "» " + CommandManager.findCommand("help").getAsMention() + " " + CommandManager.findCommand("help").getDescription(), false);
        // /connection
        embedBuilder.addField("Connection Commands",  "" +
                "» " + CommandManager.findCommand("connection").getAsMention() + " " + CommandManager.findCommand("connection").getDescription(), false);
        // /stats
        Command.Subcommand meSubcommand = CommandManager.findSubcommand(CommandManager.findCommand("stats"), "me");
        Command.Subcommand userSubcommand = CommandManager.findSubcommand(CommandManager.findCommand("stats"), "user");
        Command.Subcommand riotIdSubcommand = CommandManager.findSubcommand(CommandManager.findCommand("stats"), "riot-id");
        embedBuilder.addField("Statistic Commands", "" +
                "» " + meSubcommand.getAsMention() + " " + meSubcommand.getDescription() + "\n" +
                "» " + userSubcommand.getAsMention() + " " + userSubcommand.getDescription() + "\n" +
                "» " + riotIdSubcommand.getAsMention() + " " + riotIdSubcommand.getDescription(), false);
        embedBuilder.addField("Information Commands", "" +
                "» " + CommandManager.findCommand("map").getAsMention() + " " + CommandManager.findCommand("map").getDescription() + "\n" +
                "» " + CommandManager.findCommand("agent").getAsMention() + " " + CommandManager.findCommand("agent").getDescription(), false);


        event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Button.link(SettingsConfig.getSupportGuildInviteLink(), "Support").withEmoji(Emoji.getDiscord()),
                Button.link(SettingsConfig.getWebsiteUri() + "/redirect/github", "GitHub").withEmoji(Emoji.getGitHub()),
                Button.link(SettingsConfig.getWebsiteUri() + "/redirect/topgg", "Top.GG").withEmoji(Emoji.getTopGG())
        ).queue();

    }

}
