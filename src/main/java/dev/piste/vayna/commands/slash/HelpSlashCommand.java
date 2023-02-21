package dev.piste.vayna.commands.slash;

import dev.piste.vayna.commands.manager.SlashCommand;
import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.commands.manager.SlashCommandManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emoji;
import dev.piste.vayna.util.translations.Language;
import dev.piste.vayna.util.translations.LanguageManager;
import dev.piste.vayna.util.templates.Buttons;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/zPiste
 */
public class HelpSlashCommand implements SlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        Language language = LanguageManager.getLanguage(event.getGuild());

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-help-embed-title"))
                .addField(language.getTranslation("command-help-embed-field-1-name"), language.getTranslation("command-help-embed-field-1-text")
                        .replaceAll("%version%", ConfigManager.getSettingsConfig().getVersion()), true);
        // General
        embed.addField(language.getTranslation("command-help-embed-field-2-name"),  "" +
                "» " + SlashCommandManager.getAsJdaCommand(new HelpSlashCommand()).getAsMention() + " " + language.getTranslation("command-help-description") + "\n" +
                "» " + SlashCommandManager.getAsJdaCommand(new FeedbackSlashCommand()).getAsMention() + " " + language.getTranslation("command-feedback-description"), false);
        // /connection
        embed.addField(language.getTranslation("command-help-embed-field-3-name"),  "" +
                "» " + SlashCommandManager.getAsJdaCommand(new ConnectionSlashCommand()).getAsMention() + " " + language.getTranslation("command-connection-description"), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = SlashCommandManager.getAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = SlashCommandManager.getAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = SlashCommandManager.getAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(2);
        embed.addField(language.getTranslation("command-help-embed-field-4-name"), "" +
                "» " + subcommand1.getAsMention() + " " + language.getTranslation("command-stats-user-description") + "\n" +
                "» " + subcommand2.getAsMention() + " " + language.getTranslation("command-stats-riotid-description") + "\n" +
                "» " + subcommand3.getAsMention() + " " + language.getTranslation("command-stats-me-description") + "\n" +
                "» " + SlashCommandManager.getAsJdaCommand(new LeaderboardSlashCommand()).getAsMention() + " " + language.getTranslation("command-leaderboard-description"), false);
        // Info Commands
        embed.addField(language.getTranslation("command-help-embed-field-5-name"), "" +
                "» " + SlashCommandManager.getAsJdaCommand(new MapSlashCommand()).getAsMention() + " " + language.getTranslation("command-map-description") + "\n" +
                "» " + SlashCommandManager.getAsJdaCommand(new AgentSlashCommand()).getAsMention() + " " + language.getTranslation("command-agent-description") + "\n" +
                "» " + SlashCommandManager.getAsJdaCommand(new GamemodeSlashCommand()).getAsMention() + " " + language.getTranslation("command-gamemode-description") + "\n" +
                "» " + SlashCommandManager.getAsJdaCommand(new WeaponSlashCommand()).getAsMention() + " " + language.getTranslation("command-weapon-description") + "\n" +
                "» "  + SlashCommandManager.getAsJdaCommand(new StoreSlashCommand()).getAsMention() + " " + language.getTranslation("command-store-description"), false);

        embed.addField(language.getTranslation("command-help-embed-field-6-name"), "" +
                "» " + SlashCommandManager.getAsJdaCommand(new SettingsSlashCommand()).getAsMention() + " " + language.getTranslation("command-settings-description"), false);

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
        return LanguageManager.getLanguage().getTranslation("command-help-description");
    }
}
