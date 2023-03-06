package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.config.ConfigManager;
import dev.piste.vayna.interactions.InteractionManager;
import dev.piste.vayna.util.Embed;
import dev.piste.vayna.util.Emojis;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.templates.Buttons;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class HelpSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) {
        event.deferReply().setEphemeral(true).queue();

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-help-embed-title"))
                .addField(language.getTranslation("command-help-embed-field-1-name"), language.getTranslation("command-help-embed-field-1-text")
                        .replaceAll("%version%", ConfigManager.getSettingsConfig().getVersion()), true);
        // General
        embed.addField(language.getTranslation("command-help-embed-field-2-name"),  "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new HelpSlashCommand()).getAsMention() + " " + language.getTranslation("command-help-description") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new FeedbackSlashCommand()).getAsMention() + " " + language.getTranslation("command-feedback-description"), false);
        // /connection
        embed.addField(language.getTranslation("command-help-embed-field-3-name"),  "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new ConnectionSlashCommand()).getAsMention() + " " + language.getTranslation("command-connection-description"), false);
        // /stats & /leaderboard
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand1 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(0);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand2 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(1);
        net.dv8tion.jda.api.interactions.commands.Command.Subcommand subcommand3 = InteractionManager.getSlashCommandAsJdaCommand(new StatsSlashCommand()).getSubcommands().get(2);
        embed.addField(language.getTranslation("command-help-embed-field-4-name"), "" +
                "» " + subcommand1.getAsMention() + " " + language.getTranslation("command-stats-user-description") + "\n" +
                "» " + subcommand2.getAsMention() + " " + language.getTranslation("command-stats-riotid-description") + "\n" +
                "» " + subcommand3.getAsMention() + " " + language.getTranslation("command-stats-me-description") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new LeaderboardSlashCommand()).getAsMention() + " " + language.getTranslation("command-leaderboard-description"), false);
        // Info Commands
        embed.addField(language.getTranslation("command-help-embed-field-5-name"), "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new MapSlashCommand()).getAsMention() + " " + language.getTranslation("command-map-description") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new AgentSlashCommand()).getAsMention() + " " + language.getTranslation("command-agent-description") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new GamemodeSlashCommand()).getAsMention() + " " + language.getTranslation("command-gamemode-description") + "\n" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new WeaponSlashCommand()).getAsMention() + " " + language.getTranslation("command-weapon-description") + "\n" +
                "» "  + InteractionManager.getSlashCommandAsJdaCommand(new StoreSlashCommand()).getAsMention() + " " + language.getTranslation("command-store-description"), false);

        embed.addField(language.getTranslation("command-help-embed-field-6-name"), "" +
                "» " + InteractionManager.getSlashCommandAsJdaCommand(new SettingsSlashCommand()).getAsMention() + " " + language.getTranslation("command-settings-description"), false);

        event.getHook().setEphemeral(true).editOriginalEmbeds(embed.build()).setActionRow(
                Buttons.getSupportButton(language),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/github", "GitHub").withEmoji(Emojis.getGitHub()),
                Button.link(ConfigManager.getSettingsConfig().getWebsiteUri() + "/redirect/topgg", "Top.GG").withEmoji(Emojis.getTopGG())
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
