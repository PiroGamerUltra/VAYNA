package dev.piste.vayna.commands;

import dev.piste.vayna.manager.Command;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        Embed embed = new Embed();
        embed.setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl());
        embed.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-help-embed-title"));
        embed.addField(language.getTranslation("command-help-embed-field-1-name"), language.getTranslation("command-help-embed-field-1-text")
                .replaceAll("%version%", ConfigManager.getSettingsConfig().getVersion()), true);
        // General
        embed.addField(language.getTranslation("command-help-embed-field-2-name"),  "" +
                "» " + CommandManager.getAsJdaCommand(new HelpCommand()).getAsMention() + " " + new HelpCommand().getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new FeedbackCommand()).getAsMention() + " " + new FeedbackCommand().getDescription(), false);
        // /connection
        embed.addField(language.getTranslation("command-help-embed-field-3-name"),  "" +
                "» " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention() + " " + new ConnectionCommand().getDescription(), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(2);
        embed.addField(language.getTranslation("command-help-embed-field-4-name"), "" +
                "» " + subcommand1.getAsMention() + " " + subcommand1.getDescription() + "\n" +
                "» " + subcommand2.getAsMention() + " " + subcommand2.getDescription() + "\n" +
                "» " + subcommand3.getAsMention() + " " + subcommand3.getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new LeaderboardCommand()).getAsMention() + " " + new LeaderboardCommand().getDescription(), false);
        // Info Commands
        embed.addField(language.getTranslation("command-help-embed-field-5-name"), "" +
                "» " + CommandManager.getAsJdaCommand(new MapCommand()).getAsMention() + " " + new MapCommand().getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new AgentCommand()).getAsMention() + " " + new AgentCommand().getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new GamemodeCommand()).getAsMention() + " " + new GamemodeCommand().getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new WeaponCommand()).getAsMention() + " " + new WeaponCommand().getDescription() + "\n" +
                "» "  + CommandManager.getAsJdaCommand(new StoreCommand()).getAsMention() + " " + new StoreCommand().getDescription(), false);

        embed.addField(language.getTranslation("command-help-embed-field-6-name"), "" +
                "» " + CommandManager.getAsJdaCommand(new SettingsCommand()).getAsMention() + " " + new SettingsCommand().getDescription(), false);

        event.getHook().setEphemeral(true).editOriginalEmbeds(embed.build()).setActionRow(
                Buttons.getSupportButton(event.getGuild()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/github", "GitHub").withEmoji(Emoji.getGitHub()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/topgg", "Top.GG").withEmoji(Emoji.getTopGG())
        ).queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Get general information about the bot and a list of all available commands";
    }
}
