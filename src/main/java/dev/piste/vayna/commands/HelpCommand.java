package dev.piste.vayna.commands;

import dev.piste.vayna.Bot;
import dev.piste.vayna.manager.Command;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.manager.CommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.Language;
import dev.piste.vayna.util.LanguageManager;
import dev.piste.vayna.util.buttons.Buttons;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand implements Command {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();

        Language language = LanguageManager.getLanguage(event.getGuild());

        Embed embedBuilder = new Embed();
        embedBuilder.setAuthor(event.getUser().getName(), event.getUser().getAvatarUrl());
        embedBuilder.setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-help-embed-title"));
        embedBuilder.addField(language.getTranslation("command-help-embed-field-1-name"), language.getTranslation("command-help-embed-field-1-text")
                .replaceAll("%version%", ConfigManager.getSettingsConfig().getVersion()), true);
        // General
        embedBuilder.addField(language.getTranslation("command-help-embed-field-2-name"),  "" +
                "» " + CommandManager.getAsJdaCommand(new HelpCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new HelpCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new FeedbackCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new FeedbackCommand()).getDescription(), false);
        // /connection
        embedBuilder.addField(language.getTranslation("command-help-embed-field-3-name"),  "" +
                "» " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new ConnectionCommand()).getDescription(), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = CommandManager.getAsJdaCommand(new StatsCommand()).getSubcommands().get(2);
        embedBuilder.addField(language.getTranslation("command-help-embed-field-4-name"), "" +
                "» " + subcommand1.getAsMention() + " " + subcommand1.getDescription() + "\n" +
                "» " + subcommand2.getAsMention() + " " + subcommand2.getDescription() + "\n" +
                "» " + subcommand3.getAsMention() + " " + subcommand3.getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new LeaderboardCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new LeaderboardCommand()).getDescription(), false);
        // Info Commands
        embedBuilder.addField(language.getTranslation("command-help-embed-field-5-name"), "" +
                "» " + CommandManager.getAsJdaCommand(new MapCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new MapCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new AgentCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new AgentCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new GamemodeCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new GamemodeCommand()).getDescription() + "\n" +
                "» " + CommandManager.getAsJdaCommand(new WeaponCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new WeaponCommand()).getDescription() + "\n" +
                "» "  + CommandManager.getAsJdaCommand(new StoreCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new StoreCommand()).getDescription(), false);

        embedBuilder.addField(language.getTranslation("command-help-embed-field-6-name"), "" +
                "» " + CommandManager.getAsJdaCommand(new SettingsCommand()).getAsMention() + " " + CommandManager.getAsJdaCommand(new SettingsCommand()).getDescription(), false);

        event.getHook().setEphemeral(true).editOriginalEmbeds(embedBuilder.build()).setActionRow(
                Buttons.getSupportButton(event.getGuild()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/github", "GitHub").withEmoji(Emoji.getGitHub()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/topgg", "Top.GG").withEmoji(Emoji.getTopGG())
        ).queue();
    }

    @Override
    public void register() {
        Bot.getJDA().upsertCommand(getName(), getDescription()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Look up general information about the bot and a list of all available commands";
    }
}
